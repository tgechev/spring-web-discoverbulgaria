package com.gechev.discoverbulgaria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No fact with such name in DB.")
public class FactNotFoundException extends RuntimeException {
    public FactNotFoundException(String message) {
        super(message);
    }
}
