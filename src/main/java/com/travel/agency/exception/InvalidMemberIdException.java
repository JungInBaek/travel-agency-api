package com.travel.agency.exception;

import org.springframework.http.HttpStatus;

public class InvalidMemberIdException extends TravelApiException {

    private static final String MESSAGE = "잘못된 회원ID값입니다";

    public InvalidMemberIdException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

}
