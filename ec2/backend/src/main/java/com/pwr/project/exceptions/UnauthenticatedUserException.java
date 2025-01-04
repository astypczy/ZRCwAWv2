package com.pwr.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthenticatedUserException extends RuntimeException {
    public UnauthenticatedUserException(String message) {
        super(message);
    }
}
