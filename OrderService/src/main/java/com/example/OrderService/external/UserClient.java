package com.example.OrderService.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.OrderService.model.UserDao;

@FeignClient(name = "user-client", url = "localhost:8080", path = "/api")
public interface UserClient {
     @GetMapping(value = "/getUser")
     public UserDao getUser(@RequestParam(name = "username") String username,
               @RequestHeader("Authorization") String authorizationToken);
}
