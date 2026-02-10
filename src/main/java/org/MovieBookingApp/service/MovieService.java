package org.MovieBookingApp.service;


import org.MovieBookingApp.dto.requestDto.MovieRequestDto;
import org.MovieBookingApp.dto.responseDto.MovieResponseDto;

public interface MovieService {
    MovieResponseDto addMovie(MovieRequestDto movieRequestDto);

    MovieResponseDto updateMovie(String movieId, MovieRequestDto movieRequestDto);

    MovieResponseDto getMovieById(String movieId);

}