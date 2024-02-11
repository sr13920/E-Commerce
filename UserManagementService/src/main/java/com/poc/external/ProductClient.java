package com.poc.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.poc.model.BuyerSellerDao;
import com.poc.model.UserDao;

@FeignClient(name = "product-client", url = "localhost:8081", path = "/api")
public interface ProductClient {
     @PostMapping(value = "/updateUserProducts")
     public boolean updateUserProducts(@RequestBody BuyerSellerDao user);
}
