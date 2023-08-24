package com.liveme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {
    private String status;
    private String errorMessage;
    private static String fieldName;

    public BadRequestException() {
        this("Ошибка", "Bad Request", fieldName);
    }

    public BadRequestException(String status, String message, String fieldName) {
        super(message);
        this.status = status;
        this.errorMessage = message;
        this.fieldName = fieldName;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getErrorBody() {
        return null;
    }
}
