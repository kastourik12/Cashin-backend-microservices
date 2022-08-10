package com.example.transactionsmicroservice.controller;

import com.example.transactionsmicroservice.payload.TransactionRequest;
import com.example.transactionsmicroservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody TransactionRequest transaction) {
        return transactionService.send(transaction);
    }

}
