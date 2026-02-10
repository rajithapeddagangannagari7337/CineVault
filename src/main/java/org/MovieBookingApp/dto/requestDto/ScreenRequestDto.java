package org.MovieBookingApp.dto.requestDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenRequestDto {

    @NotNull(message = "Theater ID is required")
    private String theaterId;

    @NotBlank(message = "Screen name is required")
    private String name;

    @Min(value = 1, message = "Screen must have at least 1 seat")
    private int totalSeats;
}