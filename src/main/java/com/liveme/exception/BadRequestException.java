package com.liveme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {
    private String status;
    private String errorMessage;

    public BadRequestException() {
        this("Ошибка", "Bad Request");
    }

    public BadRequestException(String status, String message) {
        super(message);
        this.status = status;
        this.errorMessage = message;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
