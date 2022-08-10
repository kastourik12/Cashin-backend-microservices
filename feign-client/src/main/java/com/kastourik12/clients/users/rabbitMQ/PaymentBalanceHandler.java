package com.kastourik12.clients.users.rabbitMQ;

public record PaymentBalanceHandler(Long userId, Double amount, String currency) {
}
