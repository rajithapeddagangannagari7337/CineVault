package org.MovieBookingApp.service.serviceImpl;

import org.MovieBookingApp.dto.requestDto.BookingRequestDto;
import org.MovieBookingApp.dto.responseDto.BookingResponseDto;
import org.MovieBookingApp.entities.Booking;
import org.MovieBookingApp.entities.Payment;
import org.MovieBookingApp.entities.Shows;
import org.MovieBookingApp.entities.Users;
import org.MovieBookingApp.enums.BookingStatus;
import org.MovieBookingApp.enums.PaymentMethod;
import org.MovieBookingApp.exceptions.PaymentFailedException;
import org.MovieBookingApp.exceptions.ResourceNotFoundException;
import org.MovieBookingApp.exceptions.SeatNotAvailableException;
import org.MovieBookingApp.repository.BookingRepository;
import org.MovieBookingApp.repository.PaymentRepository;
import org.MovieBookingApp.repository.ShowRepository;
import org.MovieBookingApp.repository.UserRepository;
import org.MovieBookingApp.service.BookingService;
import org.MovieBookingApp.service.PaymentGatewayService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentGatewayService paymentGatewayService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto bookingRequest) {

        // Fetch User and Show
        Users user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Shows show = showRepository.findById(bookingRequest.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        List<String> requestedSeats = bookingRequest.getSeatIds();

        // Atomic seat reservation using MongoTemplate
        Query query = new Query(Criteria.where("_id").is(show.getShowId())
                .and("bookedSeatIds").nin(requestedSeats));
        Update update = new Update().push("bookedSeatIds").each(requestedSeats);

        Shows updatedShow = mongoTemplate.findAndModify(query, update, Shows.class);
        if (updatedShow == null) {
            throw new SeatNotAvailableException("Some seats are already booked");
        }

        // Calculate total amount
        double totalAmount = requestedSeats.size() * 200.0;

        // Process Payment via Stripe
        String transactionId;
        try {
            transactionId = paymentGatewayService.chargeCreditCard(bookingRequest.getPaymentToken(), totalAmount);
        } catch (Exception e) {
            // Manual rollback for seat reservation if payment fails
            mongoTemplate.update(Shows.class)
                    .matching(Criteria.where("_id").is(show.getShowId()))
                    .apply(new Update().pullAll("bookedSeatIds", requestedSeats.toArray()))
                    .first();
            throw new PaymentFailedException("Payment failed: " + e.getMessage());
        }

        // Save Booking FIRST (to avoid NULL ID Reference error)
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setBookedSeatIds(requestedSeats);
        booking.setBookingTime(LocalDateTime.now());
        booking.setNumberOfSeats(requestedSeats.size());
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setTotalAmount(totalAmount);

        Booking savedBooking = bookingRepository.save(booking);

        // Save Payment referencing the saved booking
        Payment payment = new Payment();
        payment.setAmount(totalAmount);
        payment.setTransactionId(transactionId);
        payment.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        payment.setPaymentStatus("SUCCESS");
        payment.setBooking(savedBooking);

        Payment savedPayment = paymentRepository.save(payment);

        // Update Booking with Payment reference
        savedBooking.setPayment(savedPayment);
        bookingRepository.save(savedBooking);

        return mapToResponse(savedBooking);
    }

    @Override
    public BookingResponseDto getBookingById(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
        return mapToResponse(booking);
    }

    private BookingResponseDto mapToResponse(Booking booking) {
        BookingResponseDto response = modelMapper.map(booking, BookingResponseDto.class);

        // Map display names (e.g., "A1") by filtering the embedded Seat list in the Screen entity
        List<String> seatNumbers = booking.getShow().getScreen().getSeats().stream()
                .filter(s -> booking.getBookedSeatIds().contains(s.getSeatRowName() + s.getSeatNumber()))
                .map(s -> s.getSeatRowName() + s.getSeatNumber())
                .collect(Collectors.toList());

        response.setSeatNumbers(seatNumbers);
        response.setMovieName(booking.getShow().getMovie().getMovieTitle());
        response.setTheaterName(booking.getShow().getScreen().getTheater().getTheaterName());
        response.setScreenName(booking.getShow().getScreen().getScreenName());

        return response;
    }

}