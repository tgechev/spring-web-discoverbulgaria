package com.gechev.discoverbulgaria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No poi with such name in DB.")
public class PoiNotFoundException extends RuntimeException{
    public PoiNotFoundException(String message) {
        super(message);
    }
}
