package org.MovieBookingApp.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenResponseDto {
    private String screenId;
    private String name;
    private int totalSeats;
    private String theaterName;
}