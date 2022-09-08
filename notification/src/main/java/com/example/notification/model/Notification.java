package com.example.notification.model;


import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data

public class Notification {
    @Id
    private String id;
    private String message;
    private String type;
    private String status;
    private Long userId;
}
