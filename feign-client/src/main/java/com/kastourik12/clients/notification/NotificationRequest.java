package com.kastourik12.clients.notification;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationRequest {
    private String message;
    private String type;
    private String status;
    private Long userId;
}
