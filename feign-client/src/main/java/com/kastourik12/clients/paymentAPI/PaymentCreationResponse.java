package com.kastourik12.clients.paymentAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentCreationResponse {
    private String paymentId;
    private String userId;
    private String successLink;
}
