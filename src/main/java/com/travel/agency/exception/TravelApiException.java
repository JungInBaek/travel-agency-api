package com.travel.agency.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class TravelApiException extends RuntimeException {

    private Map<String, List<String>> validation = new HashMap<>();

    public TravelApiException(String message) {
        super(message);
    }

    public TravelApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus getStatusCode();

    public void addValidation(String field, List<String> message) {
        validation.put(field, message);
    }

}
