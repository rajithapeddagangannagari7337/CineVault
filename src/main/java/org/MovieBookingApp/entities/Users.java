package org.MovieBookingApp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString(exclude = "bookings")
@Document(collection = "users")
public class Users {

    @Id
    private String userId;
    private String userName;
    @Indexed(unique = true)
    private String userEmail;
    private String password;
    private String userPhone;
}
