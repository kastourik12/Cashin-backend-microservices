package com.example.clients.payment;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "payment", path = "api/v1/payment")
public interface PaymentClient {

}
