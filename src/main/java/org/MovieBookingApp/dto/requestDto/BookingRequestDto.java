package org.MovieBookingApp.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequestDto {
    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Show ID is required")
    private String showId;

    @NotNull(message = "Must select at least one seat")
    private List<String> seatIds;

    private String paymentToken;
}