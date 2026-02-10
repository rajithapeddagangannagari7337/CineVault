package org.MovieBookingApp.service.serviceImpl;

import org.MovieBookingApp.dto.requestDto.MovieRequestDto;
import org.MovieBookingApp.dto.responseDto.MovieResponseDto;
import org.MovieBookingApp.entities.Movie;
import org.MovieBookingApp.exceptions.ResourceNotFoundException;
import org.MovieBookingApp.repository.MovieRepository;
import org.MovieBookingApp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public MovieResponseDto addMovie(MovieRequestDto movieRequestDto) {
        // 1. Create Movie entity from DTO
        Movie movie = new Movie();
        movie.setMovieTitle(movieRequestDto.getTitle());
        movie.setMovieGenre(movieRequestDto.getGenre());
        movie.setMovieDuration(movieRequestDto.getDuration());
        movie.setLanguage(movieRequestDto.getLanguage());
        movie.setReleaseDate(movieRequestDto.getReleaseDate());
        movie.setRating(movieRequestDto.getRating());

        // 2. Save to MongoDB
        Movie savedMovie = movieRepository.save(movie);

        // 3. Map to Response DTO
        MovieResponseDto response = new MovieResponseDto();
        response.setMovieId(savedMovie.getMovieId()); // String ID
        response.setTitle(savedMovie.getMovieTitle());
        response.setGenre(savedMovie.getMovieGenre());
        response.setRating(savedMovie.getRating());

        return response;
    }

    @Override
    public MovieResponseDto updateMovie(String movieId, MovieRequestDto movieRequestDto) {
        // 1. Fetch movie by String ID
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        // 2. Update fields
        movie.setMovieTitle(movieRequestDto.getTitle());
        movie.setMovieGenre(movieRequestDto.getGenre());
        movie.setMovieDuration(movieRequestDto.getDuration());
        movie.setLanguage(movieRequestDto.getLanguage());
        movie.setReleaseDate(movieRequestDto.getReleaseDate());
        movie.setRating(movieRequestDto.getRating());

        // 3. Save changes
        Movie savedMovie = movieRepository.save(movie);

        // 4. Map to Response DTO
        MovieResponseDto response = new MovieResponseDto();
        response.setMovieId(savedMovie.getMovieId()); // String ID
        response.setTitle(savedMovie.getMovieTitle());
        response.setGenre(savedMovie.getMovieGenre());
        response.setRating(savedMovie.getRating());

        return response;
    }

    @Override
    public MovieResponseDto getMovieById(String movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        MovieResponseDto response = new MovieResponseDto();
        response.setMovieId(movie.getMovieId()); // String ID
        response.setTitle(movie.getMovieTitle());
        response.setGenre(movie.getMovieGenre());
        response.setRating(movie.getRating());

        return response;
    }
}
