package org.MovieBookingApp.controller;


import org.MovieBookingApp.dto.requestDto.BookingRequestDto;
import org.MovieBookingApp.dto.responseDto.BookingResponseDto;
import org.MovieBookingApp.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings") // Base URL: localhost:8080/bookings
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // POST /bookings
    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@Valid @RequestBody BookingRequestDto request) {
        BookingResponseDto response = bookingService.createBooking(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET /bookings/{id}
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDto> getBooking(@PathVariable String bookingId) {
        BookingResponseDto response = bookingService.getBookingById(bookingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}