package com.example.transactionsmicroservice.rabbitMQ;

import com.example.transactionsmicroservice.service.TransactionService;
import com.kastourik12.amqp.RabbitMQMessageProducer;
import com.kastourik12.clients.notification.NotificationRequest;
import com.kastourik12.clients.paymentAPI.PaymentBalanceHandler;
import com.kastourik12.clients.transactions.TransactionPayload;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class Consumer {
    private Logger logger = LoggerFactory.getLogger(Consumer.class);
    private final NotificationPublisher notificationPublisher;
    private final TransactionService transactionService;
    @RabbitListener(queues = "${rabbitmq.queues.payment}")
    public void consumer(TransactionPayload transactionPayload) {
        logger.info("Consumed {} from queue", transactionPayload);
        transactionService.createTransaction(transactionPayload);
        notificationPublisher.publish(new
                NotificationRequest(
                "Transaction",
                "You recvied a new transaction from " + transactionPayload.getSenderName(),
                transactionPayload.getReceiver()
                )
        );
        notificationPublisher.publish(new
                NotificationRequest(
                "Transaction",
                "You sent a new transaction to " + transactionPayload.getReceiverName(),
                transactionPayload.getSender()
                )
        );
    }
}
