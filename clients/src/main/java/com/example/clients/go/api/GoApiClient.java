package com.example.clients.go.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("go-api")
public interface GoApiClient {
    @PostMapping("api/v1/payment/create")
    String createPayment(@RequestBody PayPalPaymentRequest request);

    @GetMapping("api/v1/payment/execute")
    PaymentDTO executePayment(@RequestParam String paymentId, @RequestParam String PayerID);

}
