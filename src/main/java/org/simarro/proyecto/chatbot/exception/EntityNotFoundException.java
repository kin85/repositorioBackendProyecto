package org.simarro.proyecto.chatbot.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    
    private final String errorCode;

    public EntityNotFoundException (String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}