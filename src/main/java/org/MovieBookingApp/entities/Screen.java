package org.MovieBookingApp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "theater")
@Document(collection = "screens")
public class Screen {

    @Id
    private String screenId;

    private String screenName;
    private int totalSeat;

    @DBRef
    private Theater theater;

    private List<Seat> seats = new ArrayList<>();

}