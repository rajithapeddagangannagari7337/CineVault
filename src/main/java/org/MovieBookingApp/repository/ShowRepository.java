package org.MovieBookingApp.repository;

import org.MovieBookingApp.entities.Shows;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepository extends MongoRepository<Shows, String> {

    List<Shows> findByMovieMovieId(String movieId);
    List<Shows> findByScreenTheaterTheaterId(String theaterId);

    // Optional: find by ID (MongoRepository already provides findById)
    Optional<Shows> findByShowId(String showId);
}
