package org.simarro.proyecto.chatbot.exception;

import lombok.Getter;

@Getter
public class DataValidationException extends RuntimeException {
    
    private final String errorCode;

    public DataValidationException (String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}