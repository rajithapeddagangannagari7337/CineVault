package org.MovieBookingApp.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentGatewayService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }


    public String chargeCreditCard(String token, double amount) throws StripeException {
        int cents = (int) (amount * 100);

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", cents);
        chargeParams.put("currency", "inr");
        chargeParams.put("source", token);
        chargeParams.put("description", "Movie Ticket Booking - CineVault");

        Charge charge = Charge.create(chargeParams);

        return charge.getId();
    }
}
