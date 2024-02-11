package com.example.OrderService.consumer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.OrderService.model.ProductDao;
import com.example.OrderService.model.BuyerSellerDao;
import com.example.OrderService.model.OrderDao;
import com.example.OrderService.repository.OrderRepo;

@Component
public class KafkaConsumer {

    @Autowired
    private OrderRepo orderRepo;

    @KafkaListener(topics = "java_in", groupId = "test-group-1", containerFactory = "kafkaListenerContainerFactory")
    @Async
    public CompletableFuture<Void> receive(BuyerSellerDao user) {
        List<OrderDao> userOrders = orderRepo.findOrdersAndProductsByUsername(user.getUsername());
        for (OrderDao o : userOrders) {
            List<ProductDao> userProducts = o.getProducts();
            for (ProductDao p : userProducts) {
                if (p.getSeller().getUsername().equals(user.getUsername())) {
                    p.setSeller(user);
                }
            }
            o.setProducts(userProducts);
            if (o.getBuyer().getUsername().equals(user.getUsername())) {
                o.setBuyer(user);
            }
        }
        orderRepo.saveAll(userOrders);
        return CompletableFuture.completedFuture(null);
    }
}
