package org.MovieBookingApp.service.serviceImpl;

import org.MovieBookingApp.dto.requestDto.TheaterRequestDto;
import org.MovieBookingApp.dto.responseDto.TheaterResponseDto;
import org.MovieBookingApp.entities.Theater;
import org.MovieBookingApp.repository.TheaterRepository;
import org.MovieBookingApp.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheaterServiceImpl implements TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    @Override
    public TheaterResponseDto addTheater(TheaterRequestDto theaterRequestDto) {
        // Create Theater entity from DTO
        Theater theater = new Theater();
        theater.setTheaterName(theaterRequestDto.getName());
        theater.setTheaterLocation(theaterRequestDto.getLocation());

        // Save to MongoDB
        Theater savedTheater = theaterRepository.save(theater);

        // Map to Response DTO
        TheaterResponseDto response = new TheaterResponseDto();
        response.setId(savedTheater.getTheaterId()); // String ID
        response.setName(savedTheater.getTheaterName());
        response.setLocation(savedTheater.getTheaterLocation());

        return response;
    }

    @Override
    public TheaterResponseDto getTheaterById(String theaterId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theater not found with ID: " + theaterId));

        TheaterResponseDto response = new TheaterResponseDto();
        response.setId(theater.getTheaterId());
        response.setName(theater.getTheaterName());
        response.setLocation(theater.getTheaterLocation());
        return response;
    }
}
