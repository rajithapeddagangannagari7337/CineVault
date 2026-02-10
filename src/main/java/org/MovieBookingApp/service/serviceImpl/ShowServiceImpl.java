package org.MovieBookingApp.service.serviceImpl;

import org.MovieBookingApp.dto.requestDto.ShowRequestDto;
import org.MovieBookingApp.dto.responseDto.ShowResponseDto;
import org.MovieBookingApp.entities.Movie;
import org.MovieBookingApp.entities.Screen;
import org.MovieBookingApp.entities.Shows;
import org.MovieBookingApp.exceptions.ResourceNotFoundException;
import org.MovieBookingApp.repository.MovieRepository;
import org.MovieBookingApp.repository.ScreenRepository;
import org.MovieBookingApp.repository.ShowRepository;
import org.MovieBookingApp.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Override
    public ShowResponseDto addShow(ShowRequestDto showRequestDto) {
        // Fetch movie
        Movie movie = movieRepository.findById(showRequestDto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        // Fetch screen
        Screen screen = screenRepository.findById(showRequestDto.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        // Create show entity
        Shows show = new Shows();
        show.setStartTime(showRequestDto.getStartTime());
        show.setEndTime(showRequestDto.getEndTime());
        show.setMovie(movie);
        show.setScreen(screen);

        // Save to MongoDB
        Shows savedShow = showRepository.save(show);

        // Map to response DTO
        ShowResponseDto response = new ShowResponseDto();
        response.setShowId(savedShow.getShowId()); // String ID
        response.setMovieTitle(savedShow.getMovie().getMovieTitle());
        response.setScreenName(savedShow.getScreen().getScreenName());
        response.setTheaterName(savedShow.getScreen().getTheater().getTheaterName());
        response.setStartTime(savedShow.getStartTime());
        response.setEndTime(savedShow.getEndTime());

        return response;
    }

    @Override
    public ShowResponseDto getShowById(String showId) {
        Shows show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        ShowResponseDto response = new ShowResponseDto();
        response.setShowId(show.getShowId()); // String ID
        response.setMovieTitle(show.getMovie().getMovieTitle());
        response.setScreenName(show.getScreen().getScreenName());
        response.setTheaterName(show.getScreen().getTheater().getTheaterName());
        response.setStartTime(show.getStartTime());
        response.setEndTime(show.getEndTime());

        return response;
    }

}
