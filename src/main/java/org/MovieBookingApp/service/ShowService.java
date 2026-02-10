package org.MovieBookingApp.service;

import org.MovieBookingApp.dto.requestDto.ShowRequestDto;
import org.MovieBookingApp.dto.responseDto.ShowResponseDto;

public interface ShowService {
    public ShowResponseDto addShow(ShowRequestDto showRequestDto);
    public ShowResponseDto getShowById(String showId);


}