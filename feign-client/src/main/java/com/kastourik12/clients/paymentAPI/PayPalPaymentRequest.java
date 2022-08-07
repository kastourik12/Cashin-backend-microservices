package com.kastourik12.clients.paymentAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayPalPaymentRequest {

    String total;
    String currency;
}
