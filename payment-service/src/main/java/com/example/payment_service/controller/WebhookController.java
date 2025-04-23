package com.example.payment_service.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    @Value("${stripe.endpoint-secret}")
    private String stripeEndpointSecret;

    @PostMapping()
    public ResponseEntity<String> stripWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature
    ) {
        Event event = null;
        try {
            event = Webhook.constructEvent(payload, signature, stripeEndpointSecret);
        } catch (SignatureVerificationException e) {
            System.out.println("Failed to verify signature");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to verify signature");
        }

        EventDataObjectDeserializer eventDataDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;

        if (eventDataDeserializer.getObject().isPresent()) {
            stripeObject = eventDataDeserializer.getObject().get();
            System.out.println(stripeObject.toJson());
        } else {
            throw new IllegalStateException(
                    String.format("Unable to deserialize event data object for %s", event));
        }

        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                System.out.println("Payment for " + paymentIntent.getAmount() + " succeeded.");
                break;
            default:
                System.out.println("Unhandled event type: " + event.getType());
                break;
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(stripeObject.toJson());
    }
}
