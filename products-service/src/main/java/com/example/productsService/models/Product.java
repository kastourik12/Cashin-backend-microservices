package com.example.productsService.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.Nullable;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("product")
@Data
public class Product {
    @Id
    private String id;
    private String name;
    @DBRef(lazy = true) @Nullable
    private List<Category> categories;
    @JsonIgnore
    private String clientId;
    @JsonIgnore
    private String userId;

    public Product(String name,String clientId,String userId) {
        this.name = name;
        this.clientId = clientId;
        this.userId = userId;
    }

}
