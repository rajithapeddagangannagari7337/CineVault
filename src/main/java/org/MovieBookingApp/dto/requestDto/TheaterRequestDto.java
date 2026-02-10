package org.MovieBookingApp.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheaterRequestDto {

    @NotBlank(message = "Theater name is required")
    private String name;
    @NotBlank(message = "Location is required")
    private String location;

}