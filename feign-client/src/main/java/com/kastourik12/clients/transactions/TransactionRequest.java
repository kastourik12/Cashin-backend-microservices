package com.kastourik12.clients.transactions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionRequest {
    private String receiver;
    private Long sender;
    private Double amount;
}
