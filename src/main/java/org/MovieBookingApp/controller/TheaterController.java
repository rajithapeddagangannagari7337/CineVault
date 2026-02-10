package org.MovieBookingApp.controller;


import org.MovieBookingApp.dto.requestDto.TheaterRequestDto;
import org.MovieBookingApp.dto.responseDto.TheaterResponseDto;
import org.MovieBookingApp.service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theaters")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @PostMapping("/add")
    public ResponseEntity<TheaterResponseDto> addTheater(@Valid @RequestBody TheaterRequestDto theaterRequestDto) {
        TheaterResponseDto response = theaterService.addTheater(theaterRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}