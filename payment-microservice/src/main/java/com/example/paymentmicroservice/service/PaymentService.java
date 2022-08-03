package com.example.paymentmicroservice.service;

import com.example.clients.go.api.GoApiClient;
import com.example.clients.go.api.PayPalPaymentRequest;
import com.example.clients.go.api.PaymentDTO;
import com.example.paymentmicroservice.model.Payment;
import com.example.paymentmicroservice.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class PaymentService {
    @Value("${go.api.host}")
    private String apiHost;
    @Value("${go.api.port}")
    private String apiPort;
    private final PaymentRepository paymentRepository;
    private final GoApiClient apiClient;
    public ResponseEntity<?> createPayment(PayPalPaymentRequest request) {

        String response = apiClient.createPayment(request);
        return ResponseEntity.ok(response);

    }
    public ResponseEntity<?> executePayment(String paymentId,String PayerID){


        PaymentDTO paymentResponse = apiClient.executePayment(paymentId,PayerID);
        Payment payment = new Payment();
        payment.setPaymentId(paymentResponse.getPaymentId());
        payment.setPayerId(paymentResponse.getPayerId());
        payment.setAmount(paymentResponse.getAmount());
        this.paymentRepository.save(payment);
        return ResponseEntity.ok("succes");
    }

}
