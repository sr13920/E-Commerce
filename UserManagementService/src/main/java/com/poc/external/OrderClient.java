package com.poc.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.poc.model.BuyerSellerDao;
import com.poc.model.UserDao;

@FeignClient(name = "order-client", url = "localhost:8082", path = "/api")
public interface OrderClient {
     @PostMapping(value = "/updateUserOrderProducts")
     public boolean updateUserOrderProducts(@RequestBody BuyerSellerDao user);
}
