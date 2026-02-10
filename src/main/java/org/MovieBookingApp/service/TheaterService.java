package org.MovieBookingApp.service;

import org.MovieBookingApp.dto.requestDto.TheaterRequestDto;
import org.MovieBookingApp.dto.responseDto.TheaterResponseDto;

public interface TheaterService {
    TheaterResponseDto addTheater(TheaterRequestDto theaterRequestDto);

    TheaterResponseDto getTheaterById(String theaterId);

}