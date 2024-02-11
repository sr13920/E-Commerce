package com.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedUserUpdateException extends RuntimeException {
    public UnauthorizedUserUpdateException(String message) {
        super(message);
    }

}
