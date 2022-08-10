package com.example.notification.service;

import com.example.notification.model.Notification;
import com.example.notification.repoistory.NotificationRepository;
import com.kastourik12.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void sendNotification(NotificationRequest request) {

        Notification notification = new Notification();
        notification.setMessage(request.message());
        notification.setType(request.type());
        notification.setStatus("unread");
        notification.setUserId(request.userId());
        notificationRepository.save(notification);
        //TODO: implement sending notification
    }
}
