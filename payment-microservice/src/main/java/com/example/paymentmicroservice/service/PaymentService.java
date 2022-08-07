package com.example.paymentmicroservice.service;


import com.example.paymentmicroservice.model.Payment;
import com.example.paymentmicroservice.repository.PaymentRepository;
import com.kastourik12.clients.paymentAPI.GoApiClient;
import com.kastourik12.clients.paymentAPI.PayPalPaymentRequest;
import com.kastourik12.clients.paymentAPI.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
@Service
public class PaymentService {
    @Value("${go.api.host}")
    private String apiHost;
    @Value("${go.api.port}")
    private String apiPort;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;



    public ResponseEntity<?> createPayment(PayPalPaymentRequest request) {
        String response = restTemplate.postForEntity(
                "http://"+apiHost+":"+apiPort+"/v1/paypal/create",
                request,
                String.class).getBody();
        return ResponseEntity.ok(response);

    }
    public ResponseEntity<?> executePayment(String paymentId,String PayerID){
        PaymentDTO paymentResponse = restTemplate.getForObject("http://"+apiHost+":"+apiPort+"/v1/paypal/execute?paymentId="+paymentId+"&PayerID="+PayerID,
                PaymentDTO.class);
        Payment payment = new Payment();
        payment.setPaymentId(paymentResponse.getPaymentId());
        payment.setPayerId(paymentResponse.getPayerId());
        payment.setAmount(paymentResponse.getAmount());
        this.paymentRepository.save(payment);

        return ResponseEntity.ok("succes");
    }

}
