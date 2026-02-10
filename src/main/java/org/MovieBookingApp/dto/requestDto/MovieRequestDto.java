package org.MovieBookingApp.dto.requestDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovieRequestDto {

    @NotBlank(message = "Movie title is required")
    private String title;

    @NotBlank(message = "Genre is required")
    private String genre;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @NotBlank(message = "Language is required")
    private String language;

    private String description;

    @NotNull(message = "Release date is required")
    private LocalDateTime releaseDate;

    @Min(value = 0, message = "Rating cannot be negative")
    private double rating;
}