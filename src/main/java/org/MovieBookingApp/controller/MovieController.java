package org.MovieBookingApp.controller;


import org.MovieBookingApp.dto.requestDto.MovieRequestDto;
import org.MovieBookingApp.dto.responseDto.MovieResponseDto;
import org.MovieBookingApp.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<MovieResponseDto> addMovie(@Valid @RequestBody MovieRequestDto movieRequestDto) {
        MovieResponseDto response = movieService.addMovie(movieRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}