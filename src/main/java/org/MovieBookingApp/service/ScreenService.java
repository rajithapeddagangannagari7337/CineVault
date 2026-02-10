package org.MovieBookingApp.service;

import org.MovieBookingApp.dto.requestDto.ScreenRequestDto;
import org.MovieBookingApp.dto.responseDto.ScreenResponseDto;

public interface ScreenService {
    ScreenResponseDto addScreen(ScreenRequestDto screenRequestDto);
}