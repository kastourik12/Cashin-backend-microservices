package com.example.clientsservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.crypto.SecretKey;

@Document
@Data
public class Client {
    @Id
    private ObjectId id;
    private String clientId;
    private String secretKey;
    private String applicationName;
    private String applicationUrl;
    private String applicationDescription;
    private ECurrency currency;
    private Long userId;

    public Client() {

    }



}
