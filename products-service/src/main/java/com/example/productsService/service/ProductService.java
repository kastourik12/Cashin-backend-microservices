package com.example.productsService.service;

import com.example.productsService.models.Category;
import com.example.productsService.models.Product;
import com.example.productsService.payload.CategoryPayload;
import com.example.productsService.payload.CategoryUpdatingPayload;
import com.example.productsService.payload.ProductCreationPayload;
import com.example.productsService.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ResponseEntity<?> addProduct(ProductCreationPayload payload, String userId) {
        Product savedProduct = productRepository.save(new Product(payload.getName(), payload.getClientId(), userId));
        List<CategoryPayload> categories = payload.getCategories();
        List<Category> savedCategories = new ArrayList<>();
        categories.forEach(category -> {
            Category savedCategory = categoryService.addCategory(Category.builder()
                    .clientId(payload.getClientId())
                    .userId(userId)
                    .price(category.getPrice())
                    .quantity(category.getQuantity())
                    .build());
            savedCategories.add(savedCategory);
        });
        savedProduct.setCategories(savedCategories);
        productRepository.save(savedProduct);
        return ResponseEntity.ok(categories);
    }


    public ResponseEntity<?> addCategoryToProduct(String id, String userId, CategoryUpdatingPayload payload) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        if (!product.getUserId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }
        Category category = categoryService.addCategory(new Category(
                payload.getPrice(), payload.getQuantity()
                , payload.getImage(), payload.getDescriptions()
                , product));
        return ResponseEntity.ok(category);
    }

    public ResponseEntity<?> getAllProducts(String clientId) {
        List<Product> products = productRepository.findAllByClientId(clientId);
        return ResponseEntity.ok(products);
    }
}
