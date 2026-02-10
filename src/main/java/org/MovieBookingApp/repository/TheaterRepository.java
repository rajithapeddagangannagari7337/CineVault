package org.MovieBookingApp.repository;

import org.MovieBookingApp.entities.Theater;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends MongoRepository<Theater, String> {
}
