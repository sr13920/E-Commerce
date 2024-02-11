package com.example.OrderService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedUpdateException extends RuntimeException {
    public UnauthorizedUpdateException(String message) {
        super(message);
    }
}
