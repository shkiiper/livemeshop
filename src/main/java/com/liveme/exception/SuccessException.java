package com.liveme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class SuccessException extends Exception {
    private String status;
    private String message;

    public SuccessException(String message) {
        this("Успешно", message);
    }

    public SuccessException(String status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
