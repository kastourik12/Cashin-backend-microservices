package com.example.usersservice.services;

import com.example.usersservice.models.CustomUser;
import com.example.usersservice.repositories.CustomUserRepository;
import com.kastourik12.amqp.RabbitMQMessageProducer;
import com.kastourik12.clients.transactions.TransactionRequest;
import com.kastourik12.clients.transactions.TransactionPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserService {
    private final CustomUserRepository customUserRepository;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    @Transactional
    public void updateCredit( CustomUser user, Double balance) {
            user.setCredit(user.getCredit() + balance);
            customUserRepository.save(user);
    }

    public CustomUser getUser(Long id) {
        return customUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " ));
    }



    private  CustomUser getUserByUserName(String userName) {
        CustomUser user = customUserRepository.findByUsernameOrEmailOrPhone(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    public ResponseEntity<?> UpdateCreditAndPublishTransaction(TransactionRequest transactionRequest) {
        CustomUser sender = customUserRepository.findById(transactionRequest.getSender()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        CustomUser receiver = getUserByUserName(transactionRequest.getReceiver());
        updateCredit(sender,-transactionRequest.getAmount());
        updateCredit(receiver,transactionRequest.getAmount());
        rabbitMQMessageProducer.publish(new TransactionPayload(
                sender.getId(),
                receiver.getId(),
                transactionRequest.getAmount(),
                sender.getUsername(),
                receiver.getUsername()
                ),"transactions","transaction");
        return ResponseEntity.ok("transaction done");

    }
}
