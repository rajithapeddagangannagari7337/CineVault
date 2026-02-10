package org.MovieBookingApp.controller;


import org.MovieBookingApp.dto.requestDto.ScreenRequestDto;
import org.MovieBookingApp.dto.responseDto.ScreenResponseDto;
import org.MovieBookingApp.service.ScreenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/screens")
public class ScreenController {

    @Autowired
    private ScreenService screenService;

    @PostMapping("/add")
    public ResponseEntity<ScreenResponseDto> addScreen(@Valid @RequestBody ScreenRequestDto screenRequestDto) {
        ScreenResponseDto response = screenService.addScreen(screenRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}