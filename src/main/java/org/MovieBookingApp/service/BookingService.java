package org.MovieBookingApp.service;

import org.MovieBookingApp.dto.requestDto.BookingRequestDto;
import org.MovieBookingApp.dto.responseDto.BookingResponseDto;

public interface BookingService {

    //Create a new booking
    BookingResponseDto createBooking(BookingRequestDto bookingRequest);
    //Get booking details by booking ID
    BookingResponseDto getBookingById(String bookingId);

}