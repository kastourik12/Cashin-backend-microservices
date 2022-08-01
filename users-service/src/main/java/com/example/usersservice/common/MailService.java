package com.example.usersservice.common;

import com.example.usersservice.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
   

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("spring@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            if (notificationEmail.getSubject().equals("Transaction")){
                log.info("Transaction email sent to " + notificationEmail.getRecipient());
            } else {
                log.info("Verification email sent to " + notificationEmail.getRecipient());
            }
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new CustomException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }

}
