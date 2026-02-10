package org.MovieBookingApp.entities;

import org.MovieBookingApp.enums.BookingStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"user", "show"})
@Document(collection = "bookings")
public class Booking {

    @Id
    private String bookingId;
    private int numberOfSeats;
    private LocalDateTime bookingTime;
    private BookingStatus bookingStatus;
    private double totalAmount;

    @DBRef
    private Users user;

    @DBRef
    private Shows show;

    private List<String> bookedSeatIds = new ArrayList<>();

    @DBRef
    private Payment payment;
}
