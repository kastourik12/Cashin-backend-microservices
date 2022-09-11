package com.example.productsService.repositories;

import com.example.productsService.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findAllByProductIdAndClientId(String productId, String clientId);
}

