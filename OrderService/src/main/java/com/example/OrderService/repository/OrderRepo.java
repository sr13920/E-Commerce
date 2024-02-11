package com.example.OrderService.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.example.OrderService.model.OrderDao;

public interface OrderRepo extends MongoRepository<OrderDao, String> {
    @Query("{'buyer.username':?0}")
    List<OrderDao> findOrdersByUsername(String buyer);

    @Query("{$or:[{'products.seller.username':?0},{'buyer.username':?0}]}")
    List<OrderDao> findOrdersAndProductsByUsername(String buyerSeller);

    @Query("{'products.pid':?0}")
    List<OrderDao> findOrdersByPid(String pid);
}