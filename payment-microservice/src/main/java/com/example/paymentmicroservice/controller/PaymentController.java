package com.example.paymentmicroservice.controller;

import com.example.paymentmicroservice.service.PaymentService;
import com.kastourik12.clients.paymentAPI.PayPalPaymentRequest;
import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment/")
@RequiredArgsConstructor
public class PaymentController {
    private Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;
    @PostMapping("create")
    public ResponseEntity<?> createPayment(@RequestBody PayPalPaymentRequest request,@RequestHeader("X-auth-user-id") String userId) {
        logger.info("inside Payment microserivce : creating payment request");
        return this.paymentService.createPayment(request,userId);
    }
    @GetMapping("execute")
    public ResponseEntity<?> executePayment(@RequestParam String paymentId, @RequestParam String PayerID){
        logger.info("inside Payment microserivce : executing created payment ");
        return this.paymentService.executePayment(paymentId,PayerID);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllPayments(@RequestHeader("X-auth-user-id") String userId){
        logger.info("inside Payment microserivce : getting all payments ");
        return this.paymentService.getAllPayments(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable String id,@RequestHeader("X-auth-user-id") String userId){
        logger.info("inside Payment microserivce : getting payment by id ");
        return this.paymentService.getPaymentById(id,userId);
    }

}
