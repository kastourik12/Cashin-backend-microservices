package com.example.usersmicroservice.events.listeners;

import com.example.usersmicroservice.common.MailService;
import com.example.usersmicroservice.common.NotificationEmail;
import com.example.usersmicroservice.events.UserRegistrationEvent;
import com.example.usersmicroservice.security.verficationKey.VerificationTokenRepository;
import com.example.usersmicroservice.security.verficationKey.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationListener implements ApplicationListener<UserRegistrationEvent> {

    private final MailService mailService;
    private final VerificationTokenService verificationTokenService;

    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        String username = event.getUser().getUsername();
        String token = verificationTokenService.generateVerificationToken(username);
        String message = "Please, follow the link below to verify your email address: " +
                "http://localhost:8123/api/auth/accountVerification/" + token;
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
        event.getUser().getEmail(), message));

    }
}
