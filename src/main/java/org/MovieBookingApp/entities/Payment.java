package org.MovieBookingApp.entities;

import org.MovieBookingApp.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString(exclude = "booking")
@Document(collection = "payments")
public class Payment {

    @Id
    private String paymentId;
    private double amount;
    private PaymentMethod paymentMethod;
    private String transactionId;
    private String paymentStatus;

    //One Payment -> One Booking
    @DBRef
    private Booking booking;
}
