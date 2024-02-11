package com.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidRoleNameException extends Exception {
    public InvalidRoleNameException(String message) {
        super(message);
    }
}
