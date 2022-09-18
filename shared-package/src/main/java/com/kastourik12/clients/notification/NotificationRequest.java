package com.kastourik12.clients.notification;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



public record NotificationRequest(String message, String type,Long userId ){
}
