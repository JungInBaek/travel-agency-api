package com.travel.agency.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation;

    public void addValidation(String field, String defaultMessage) {
        validation.put(field, defaultMessage);
    }

}
