package com.example.fundmatch.config;

import com.stripe.Stripe;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    public StripeConfig() {
        Stripe.apiKey = "sk_test_51OgSTnFbafuC7MxDJvvz5nX0qIXHumNTa7wKZRTdX8ELjHFwfxNPz4CxHZZRozvsO48KnxfucrM35w0thlFGX4sL00GZQSHpEK";
    }
}