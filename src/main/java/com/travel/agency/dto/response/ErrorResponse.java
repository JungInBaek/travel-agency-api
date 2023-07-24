package com.travel.agency.dto.response;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation;

    public void addValidation(String field, String defaultMessage) {
        validation.put(field, defaultMessage);
    }

}
