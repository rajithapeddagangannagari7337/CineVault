package org.MovieBookingApp.repository;

import org.MovieBookingApp.entities.Screen;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends MongoRepository<Screen, String> {
}
