package com.example.OrderService.consumer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.OrderService.model.OrderDao;
import com.example.OrderService.model.ProductDao;
import com.example.OrderService.repository.OrderRepo;

@Component
public class KafkaProductConsumer {

    @Autowired
    private OrderRepo orderRepo;

    @KafkaListener(topics = "product_update", groupId = "test-group-3", containerFactory = "kafkaProductListenerContainerFactory")
    @Async
    public CompletableFuture<Void> receive(ProductDao product) {
        System.out.println(product);
        List<OrderDao> userOrders = orderRepo.findOrdersByPid(product.getPid());
        for (OrderDao o : userOrders) {
            List<ProductDao> userProducts = o.getProducts();
            for (ProductDao p : userProducts) {
                if (p.getPid().equals(product.getPid())) {
                    p.setCategory(product.getCategory());
                    p.setPrice(product.getPrice());
                    p.setProductname(product.getProductname());
                }
            }
            o.setProducts(userProducts);
        }
        orderRepo.saveAll(userOrders);
        return CompletableFuture.completedFuture(null);
    }
}
