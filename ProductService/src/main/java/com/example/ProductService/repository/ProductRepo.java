package com.example.ProductService.repository;

import com.example.ProductService.model.ProductDao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProductRepo extends MongoRepository<ProductDao, String> {
    @Query("{'seller.username':?0}")
    List<ProductDao> findProductsbyUsername(String seller);
}