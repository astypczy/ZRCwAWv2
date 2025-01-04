package com.pwr.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJWTException extends AuthenticationException {
    public InvalidJWTException(String exception){
        super(exception);
    }
}
