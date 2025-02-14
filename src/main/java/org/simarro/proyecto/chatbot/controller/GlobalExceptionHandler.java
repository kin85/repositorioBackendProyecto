package org.simarro.proyecto.chatbot.controller;

import java.util.HashMap;
import java.util.Map;

import org.simarro.proyecto.chatbot.exception.BindingResultErrorsResponse;
import org.simarro.proyecto.chatbot.exception.BindingResultException;
import org.simarro.proyecto.chatbot.exception.CustomErrorResponse;
import org.simarro.proyecto.chatbot.exception.DataValidationException;
import org.simarro.proyecto.chatbot.exception.EntityAlreadyExistsException;
import org.simarro.proyecto.chatbot.exception.EntityIllegalArgumentException;
import org.simarro.proyecto.chatbot.exception.EntityNotFoundException;
import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.helper.DataIntegrityViolationAnalyzer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private final DataIntegrityViolationAnalyzer analyzer;

    @ExceptionHandler(EntityIllegalArgumentException.class) // Datos incorrectos
    public ResponseEntity<CustomErrorResponse> handleEntityIllegalArgumentException(EntityIllegalArgumentException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class) // No se ha encontrado el dato
    public ResponseEntity<CustomErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class) // Se intenta crear un dato con una PK que ya existe
    public ResponseEntity<CustomErrorResponse> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataValidationException.class) // Validación incorrecta de un atributo
    public ResponseEntity<CustomErrorResponse> handleDataValidationException(DataValidationException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindingResultException.class) // Errores en las validaciones de BindingResult en la entidad
    public ResponseEntity<BindingResultErrorsResponse> handleBindingResultException(BindingResultException ex) {
        BindingResultErrorsResponse response = new BindingResultErrorsResponse(ex.getErrorCode(), ex.getValidationErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Error en las validaciones de los datos
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        @SuppressWarnings("null")
        String detailedMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        String errorCode = analyzer.analyzeErrorCode(detailedMessage);
        String userMessage = analyzer.analyzeUserMessage(detailedMessage);

        CustomErrorResponse response = new CustomErrorResponse(errorCode, userMessage);
        response.setDetailedMessage(detailedMessage);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class) // Datos suministrados en la petición HTTP incorrectos
    public ResponseEntity<CustomErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        CustomErrorResponse response = new CustomErrorResponse("DATA_CONVERSION_ERROR", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FiltroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponse>  handleFiltroException(FiltroException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getDescripcionError(),ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}