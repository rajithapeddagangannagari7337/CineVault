package org.MovieBookingApp.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowResponseDto {
    private String showId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String movieTitle;
    private String screenName;
    private String theaterName;
}