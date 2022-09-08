package com.kastourik12.clients.transactions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class TransactionPayload {
    private Long receiver;
    private Long sender;
    private Double amount;
    private String senderName;
    private String receiverName;
}
