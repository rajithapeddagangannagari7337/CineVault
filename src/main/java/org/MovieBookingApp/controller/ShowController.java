package org.MovieBookingApp.controller;


import org.MovieBookingApp.dto.requestDto.ShowRequestDto;
import org.MovieBookingApp.dto.responseDto.ShowResponseDto;
import org.MovieBookingApp.service.ShowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/add")
    public ResponseEntity<ShowResponseDto> addShow(@Valid @RequestBody ShowRequestDto showRequestDto) {
        ShowResponseDto response = showService.addShow(showRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{showId}")
    public ResponseEntity<ShowResponseDto> getShowById(@PathVariable String showId) {
        ShowResponseDto response = showService.getShowById(showId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}