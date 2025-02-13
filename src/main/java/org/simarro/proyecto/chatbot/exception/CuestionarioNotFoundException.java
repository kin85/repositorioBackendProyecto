package org.simarro.proyecto.chatbot.exception;

public class CuestionarioNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public CuestionarioNotFoundException (String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CuestionarioNotFoundException (String message) {
        super(message);
        errorCode = "NOT_FOUND";
    }

    public String getErrorCode() {
        return errorCode;
    }
}