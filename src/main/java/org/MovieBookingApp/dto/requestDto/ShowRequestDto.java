package org.MovieBookingApp.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowRequestDto {
    private LocalDateTime startTime;

    private LocalDateTime endTime;
    private double priceMultiplier;

    private String movieId;
    private String screenId;
}