package org.MovieBookingApp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = "shows")
@Document(collection = "movies")
public class Movie {

    @Id
    private String movieId;
    private String movieTitle;
    private String movieGenre;
    private int movieDuration;
    private String language;
    private String description;
    private LocalDateTime releaseDate;
    private double rating;
}
