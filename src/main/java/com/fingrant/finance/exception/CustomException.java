package com.fingrant.finance.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final String message;
    private final String errorCode;

    public CustomException(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
