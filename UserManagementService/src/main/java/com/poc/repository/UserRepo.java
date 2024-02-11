package com.poc.repository;

import com.poc.model.UserDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserDao, Integer> {

	UserDao findByUsername(String username);
}