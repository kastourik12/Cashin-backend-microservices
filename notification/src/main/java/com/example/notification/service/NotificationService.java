package com.example.notification.service;

import com.example.notification.model.Notification;
import com.example.notification.repoistory.NotificationRepository;
import com.kastourik12.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final WebSocketService webSocketService;
    public ResponseEntity<?> getAllNotification(String userId) {
        return ResponseEntity.ok(notificationRepository.findAllByUserId(userId));
    }



    public void saveNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setMessage(request.message());
        notification.setType(request.type());
        notification.setStatus("unread");
        notification.setUserId(request.userId());
        notificationRepository.save(notification);
        webSocketService.sendNotification(request);//TODO: implement sending notification
    }
}
