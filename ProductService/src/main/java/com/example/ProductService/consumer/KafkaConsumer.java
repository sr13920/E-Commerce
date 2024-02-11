package com.example.ProductService.consumer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.ProductService.model.ProductDao;
import com.example.ProductService.model.SellerDao;
import com.example.ProductService.repository.ProductRepo;

@Component
public class KafkaConsumer {

    @Autowired
    private ProductRepo productRepo;

    @Async
    @KafkaListener(topics = "java_in", groupId = "test-group-2")
    public CompletableFuture<Void> receive(SellerDao user) {
        List<ProductDao> userProducts = productRepo.findProductsbyUsername(user.getUsername());
        for (ProductDao p : userProducts) {
            p.setSeller(user);
        }
        productRepo.saveAll(userProducts);
        return CompletableFuture.completedFuture(null);
    }
}
