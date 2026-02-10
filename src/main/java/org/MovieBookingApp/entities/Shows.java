package org.MovieBookingApp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"movie", "screen"})
@Document(collection = "shows")
public class Shows {

    @Id
    private String showId; // MongoDB ObjectId

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @DBRef
    private Movie movie;

    @DBRef
    private Screen screen;

    private Set<String> bookedSeatIds = new HashSet<>();
}
