package com.example.transactionsmicroservice.service;

import com.example.transactionsmicroservice.model.Transaction;
import com.example.transactionsmicroservice.payload.TransactionRequest;
import com.example.transactionsmicroservice.repository.TransactionRepository;
import com.kastourik12.amqp.RabbitMQMessageProducer;
import com.kastourik12.clients.transactions.TransactionBalanceHandler;
import com.kastourik12.clients.users.UsersClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final RabbitMQMessageProducer rabbitMqMessageProducer;
    private final TransactionRepository transactionRepository;
    private final UsersClient usersClient;
    public ResponseEntity<?> send(TransactionRequest transactionRequest,String userId) {
        Double senderBalance = usersClient.getBalance(userId).getBody();
        if(senderBalance < transactionRequest.getAmount()) {
            return ResponseEntity.badRequest().body("Not enough money");
        }
        else {
            Transaction transaction = new Transaction();
            transaction.setAmount(transactionRequest.getAmount());
            transaction.setSender(userId);
            transaction.setReceiver(transactionRequest.getReceiver());
            transactionRepository.save(transaction);
            TransactionBalanceHandler transactionBalanceHandler = new TransactionBalanceHandler(
                    transaction.getReceiver(),
                    transaction.getSender(),
                    transaction.getAmount(),
                    transaction.getCurrency());
            rabbitMqMessageProducer.publish(transactionBalanceHandler,"users.exchange","users.transaction.routing-key");
            return ResponseEntity.ok().body("Transaction successful");
        }
    }
}
