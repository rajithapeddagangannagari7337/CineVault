package org.MovieBookingApp.dto.responseDto;


import org.MovieBookingApp.enums.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BookingResponseDto {
    private String bookingId;
    private int numberOfSeats;
    private double totalAmount;
    private BookingStatus status;
    private LocalDateTime bookingTime;

    private String movieName;
    private String theaterName;
    private String screenName;
    private List<String> seatNumbers;
}