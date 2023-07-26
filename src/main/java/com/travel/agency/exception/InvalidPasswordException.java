package com.travel.agency.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends TravelApiException {

    private static final String MESSAGE = "잘못된 비밀번호입니다";

    public InvalidPasswordException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

}
