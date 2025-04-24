package com.example.payment_service.controller;

import com.example.payment_service.dto.OrderRequest;
import com.example.payment_service.dto.StripeResponse;
import com.example.payment_service.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment/v1")
public class ProductCheckoutController {

    private final StripeService stripeService;

    public ProductCheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProduct(@RequestBody OrderRequest orderRequest) {
        StripeResponse stripeResponse = stripeService.checkoutProduct(orderRequest);
        return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);
    }

}
