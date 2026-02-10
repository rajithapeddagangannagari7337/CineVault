package org.MovieBookingApp.service.serviceImpl;

import org.MovieBookingApp.dto.requestDto.ScreenRequestDto;
import org.MovieBookingApp.dto.responseDto.ScreenResponseDto;
import org.MovieBookingApp.entities.Screen;
import org.MovieBookingApp.entities.Seat;
import org.MovieBookingApp.entities.Theater;
import org.MovieBookingApp.exceptions.ResourceNotFoundException;
import org.MovieBookingApp.repository.ScreenRepository;
import org.MovieBookingApp.repository.TheaterRepository;
import org.MovieBookingApp.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScreenServiceImpl implements ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Override
    @Transactional // Good practice even in Mongo for logical grouping
    public ScreenResponseDto addScreen(ScreenRequestDto screenRequestDto) {

        // 1. Validate Theater exists (Critical for DBRef)
        Theater theater = theaterRepository.findById(screenRequestDto.getTheaterId())
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with ID: " + screenRequestDto.getTheaterId()));

        // 2. Initialize Screen
        Screen screen = new Screen();
        screen.setScreenName(screenRequestDto.getName());
        screen.setTotalSeat(screenRequestDto.getTotalSeats());
        screen.setTheater(theater);

        // 3. GENERATE SEATS (Fixed Logic)
        List<Seat> seats = new ArrayList<>();
        int totalSeats = screenRequestDto.getTotalSeats();
        int seatsPerRow = 10; // Fixed width

        // Calculate rows needed (Math.ceil equivalent)
        int numRows = (totalSeats + seatsPerRow - 1) / seatsPerRow;

        char rowChar = 'A';
        int seatsCreated = 0;

        for (int i = 0; i < numRows; i++) {
            String rowName = String.valueOf(rowChar);

            // Inner loop: Generate seats for this row
            for (int j = 1; j <= seatsPerRow; j++) {
                if (seatsCreated >= totalSeats) break; // Stop if we hit the limit

                Seat seat = new Seat();
                seat.setSeatRowName(rowName);
                seat.setSeatNumber(j);
                // Note: In Mongo, we usually embed seats in Screen,
                // but if using DBRef, you might need to save seats separately first.
                // Assuming Embeddable here based on standard Mongo patterns:

                seats.add(seat);
                seatsCreated++;
            }
            rowChar++;
        }

        screen.setSeats(seats);

        // 4. Save Screen
        // MongoDB will save the Screen document containing the list of Seat objects (if embedded)
        Screen savedScreen = screenRepository.save(screen);

        // 5. Update Theater (Optional but recommended for Bi-directional Mongo)
        // If Theater has a List<Screen>, you should update it here
        // theater.getScreens().add(savedScreen);
        // theaterRepository.save(theater);

        // 6. Map to DTO
        ScreenResponseDto response = new ScreenResponseDto();
        response.setScreenId(savedScreen.getScreenId());
        response.setName(savedScreen.getScreenName());
        response.setTotalSeats(savedScreen.getTotalSeat());
        response.setTheaterName(savedScreen.getTheater().getTheaterName());

        return response;
    }
}