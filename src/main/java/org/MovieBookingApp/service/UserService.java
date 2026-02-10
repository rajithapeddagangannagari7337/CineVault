package org.MovieBookingApp.service;


import org.MovieBookingApp.dto.requestDto.UserRequestDto;
import org.MovieBookingApp.dto.responseDto.UserResponseDto;

public interface UserService {
    UserResponseDto addUser(UserRequestDto userRequestDto);
    UserResponseDto getUserByEmail(String email);
}