package com.example.paymentmicroservice.service;


import com.example.paymentmicroservice.exception.CustomException;
import com.example.paymentmicroservice.model.MethodProvider;
import com.example.paymentmicroservice.model.Payment;
import com.example.paymentmicroservice.repository.PaymentRepository;
import com.kastourik12.clients.notification.NotificationRequest;
import com.kastourik12.clients.paymentAPI.PayPalPaymentRequest;
import com.kastourik12.clients.paymentAPI.PaymentCreationResponse;
import com.kastourik12.clients.paymentAPI.PaymentDTO;
import com.kastourik12.clients.paymentAPI.PaymentBalanceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PaymentService {
    @Value("${go.api.host}")
    private String apiHost;
    @Value("${go.api.port}")
    private String apiPort;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    private final amqpPublisher amqpPublisher;



    public ResponseEntity<?> createPayment(PayPalPaymentRequest request, String userId) {
        String url = "http://" + apiHost + ":"+ apiPort + "/v1/paypal/create?userId=" + userId;
        PaymentCreationResponse response = restTemplate.postForObject(
                url,
                request,
                PaymentCreationResponse.class);
        if(response != null) {
            Payment payment = new Payment();
            payment.setUserId(Long.parseLong(response.getUserId()));
            payment.setId(response.getPaymentId());
            this.paymentRepository.save(payment);
            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
    public ResponseEntity<?> executePayment(String paymentId,String PayerID) {
        PaymentDTO paymentResponse = restTemplate.getForObject(
                "http://"+apiHost+":"+apiPort+"/v1/paypal/execute?paymentId="+paymentId+"&PayerID="+PayerID,
                PaymentDTO.class);
        Payment payment = this.paymentRepository.findById(paymentResponse.getPaymentId())
                .orElseThrow(
                        () -> new CustomException("Payment not found")
                );
        payment.setPayerId(paymentResponse.getPayerId());
        payment.setAmount(paymentResponse.getAmount());
        payment.setCreatedAt(Instant.now());
        payment.setClient(MethodProvider.PAYPAL);
        this.paymentRepository.save(payment);
        PaymentBalanceHandler paymentBalanceHandler = new PaymentBalanceHandler(
                payment.getUserId(),
                Double.parseDouble(paymentResponse.getAmount()),
                paymentResponse.getCurrency());
        amqpPublisher.publish(paymentBalanceHandler);
        NotificationRequest notificationRequest = new NotificationRequest(
                "Payment completed successfully",
                "Payment",
                payment.getUserId()
        );
        amqpPublisher.publish(notificationRequest);
        return ResponseEntity.ok("succes");
    }

    public ResponseEntity<?> getAllPayments(String userId) {
        List<Payment> payments = this.paymentRepository.findAllByUserId(userId);
        return ResponseEntity.ok(payments);
    }

    public ResponseEntity<?> getPaymentById(String id, String userId) {
        Payment payment = this.paymentRepository.findById(id).orElseThrow(() -> new CustomException("Payment not found"));
        if(payment.getUserId().equals(userId)) {
            return ResponseEntity.ok(payment);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
