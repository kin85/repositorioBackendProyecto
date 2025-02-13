package org.simarro.proyecto.chatbot.filters;

import lombok.Getter;

@Getter
public class FiltroException  extends RuntimeException{
    private final String errorCode;
    private final String message;
    private String descripcionError;

    public FiltroException(String errorCode,String message,String descripcionError) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.descripcionError=descripcionError;
    }

    public FiltroException(String errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}