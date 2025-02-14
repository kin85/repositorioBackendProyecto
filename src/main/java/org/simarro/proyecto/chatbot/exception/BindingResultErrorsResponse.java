package org.simarro.proyecto.chatbot.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BindingResultErrorsResponse {
    @Schema(example = "ERRROR_CODE_STRING", description = "Código del error (String)")
    private String errorCode;
    @Schema(example = "{ \"primerAtributoConError\": \"Descripción del error del primer atributo\", " +
            "\"segundoAtributoConError\": \"Descripción del error del segundo atributo\" }", 
            description = "Descripción de los errores de validación de los atributos del registro")

    private Map<String, String> validationErrors;
    @Schema(example = "2025-01-31T12:59:10.123456789", description = "Timestamp de cuando sucedio el error")
    private String timestamp;

    public BindingResultErrorsResponse(String errorCode, Map<String, String> validationErrors) {
        this.errorCode = errorCode;
        this.validationErrors = validationErrors;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}