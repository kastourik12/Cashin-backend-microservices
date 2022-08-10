package com.example.notification.rabbitMQ;



import com.example.notification.service.MailService;
import com.kastourik12.clients.notification.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class EmailConsumer {
    private MailService mailService;
    @RabbitListener(queues = "${rabbitmq.queues.email}")
    public void consumer(NotificationEmail emailRequest) {
       this.mailService.sendMail(emailRequest);
    }
}
