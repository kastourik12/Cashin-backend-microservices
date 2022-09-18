package com.kastourik12.clients.transactions;

import lombok.AllArgsConstructor;
import lombok.Data;


public record TransactionPayload(Long receiver,Long sender,Double amount,String senderName,String receiverName ){

}
