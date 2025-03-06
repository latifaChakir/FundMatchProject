package com.example.fundmatch.service.impl;

import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public Charge createCharge(String token, double amount) throws Exception {
        ChargeCreateParams params =
                ChargeCreateParams.builder()
                        .setAmount((long) (amount * 100))
                        .setCurrency("usd")
                        .setSource(token)
                        .build();

        return Charge.create(params);
    }
}

