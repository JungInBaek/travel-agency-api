package com.travel.agency.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class TravelApiException extends RuntimeException {

    private Map<String, String> validation = new HashMap<>();

    public TravelApiException(String message) {
        super(message);
    }

    public TravelApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus getStatusCode();

    public void addValidation(String field, String message) {
        validation.put(field, message);
    }

}
