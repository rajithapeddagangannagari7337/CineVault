package org.MovieBookingApp.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovieResponseDto {
    private String movieId;
    private String title;
    private String genre;
    private int duration;
    private double rating;
    private LocalDateTime releaseDate;
}