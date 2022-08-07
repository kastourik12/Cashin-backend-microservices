package com.kastourik12.clients.paymentAPI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "GOAPI",path = "api/v1/payment/")
public interface GoApiClient {
    @PostMapping("create")
    String createPayment(@RequestBody PayPalPaymentRequest request);

    @GetMapping("execute")
    PaymentDTO executePayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String PayerID);

}
