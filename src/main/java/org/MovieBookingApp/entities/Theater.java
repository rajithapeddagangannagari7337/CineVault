package org.MovieBookingApp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString(exclude = "screens")
@Document(collection = "theaters")
public class Theater {

    @Id
    private String theaterId;
    private String theaterName;
    private String theaterLocation;
}
