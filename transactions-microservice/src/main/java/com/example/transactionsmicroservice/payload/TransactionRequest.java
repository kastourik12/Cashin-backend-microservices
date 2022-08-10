package com.example.transactionsmicroservice.payload;


import lombok.Data;

@Data
public class TransactionRequest {
    private String sender;

    private String receiver;

    private Double amount;

}
