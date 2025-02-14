package org.simarro.proyecto.chatbot.exception;

public class UsuarioNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public UsuarioNotFoundException (String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public UsuarioNotFoundException (String message) {
        super(message);
        errorCode = "NOT_FOUND";
    }

    public String getErrorCode() {
        return errorCode;
    }
}