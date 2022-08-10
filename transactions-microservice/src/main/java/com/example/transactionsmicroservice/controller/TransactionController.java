package com.example.transactionsmicroservice.controller;

import com.example.transactionsmicroservice.payload.TransactionRequest;
import com.example.transactionsmicroservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private Logger logger = LoggerFactory.getLogger(TransactionController.class);
    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody TransactionRequest transaction,@RequestHeader("X-auth-user-id") String userId) {
        logger.info("inside transactions service : send method");
        return transactionService.send(transaction,userId);
    }

}
