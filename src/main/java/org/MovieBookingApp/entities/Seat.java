package org.MovieBookingApp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class Seat {

    private String seatRowName;
    private int seatNumber;
    private boolean isBooked = false;

}