package com.example.payment_service.service;

import com.example.payment_service.dto.OrderRequest;
import com.example.payment_service.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    @Value("${stripe.key}")
    private String stripeSecretKey;

    public StripeResponse checkoutProduct(OrderRequest orderRequest) {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(orderRequest.getName())
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(orderRequest.getCurrency() == null ? "USD" : orderRequest.getCurrency())
                        .setUnitAmount(orderRequest.getAmount())
                        .setProductData(productData)
                        .build();


        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(orderRequest.getQuantity())
                .setPriceData(priceData).build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8085/payment/v1/success")
                .setCancelUrl("http://localhost:8085/payment/v1/cancel")
                .addLineItem(lineItem)
                .build();

        Session session = null;

        try {
            session = Session.create(params);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        StripeResponse stripeResponse = new StripeResponse();
        stripeResponse.setStatus("SUCCESS");
        stripeResponse.setMessage("payment successful");
        stripeResponse.setSessionId(session.getId());
        stripeResponse.setSessionUrl(session.getUrl());

        return stripeResponse;
    }
}
