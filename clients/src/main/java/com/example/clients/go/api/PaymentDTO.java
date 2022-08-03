package com.example.clients.go.api;

import lombok.Data;

@Data
public class PaymentDTO {
  private String paymentId ;
  private String payerId ;
  private String Amount;
  private String currency;
}
