package org.simarro.proyecto.chatbot.helper;

import java.util.HashMap;
import java.util.Map;

import org.simarro.proyecto.chatbot.exception.BindingResultException;
import org.springframework.validation.BindingResult;

/**
 * Clase utilitaria para ayudar con operaciones comunes en controladores.
 * 
 * Proporciona métodos estáticos para manejar tareas repetitivas como la
 * validación de errores een los controladores
 */
public class BindingResultHelper {

    private BindingResultHelper(){
        //Constructor privado para prevenir instanciación 
        throw new UnsupportedOperationException("Esta clase no puede ser instanciada.");
    }

    /**
     * Extrae los errores de validacion de un BindingResult y los organiza en un mapa
     * 
     * @param bindingResult     Resultado de la validacion, tipicamente asociado con 
     *                          anotaciones como  @Valid
     * @return  Un para donde las claves son los nombres de los campos y los valores 
     *          son los mensajes de error.
     */
    public static Map<String, String> extractErrors(BindingResult bindingResult){
        Map<String, String>errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error-> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }



    /**
     * Valida el rsultado de la vinculacion  (binding) de un objeto y lanza una
     * excepción si se detectac errores de validación.
     * 
     * Este metodo es util para centralizar la logica de validacion de objetos
     * enotados con trstricciones de Bean VAlidation (@Valid) en los controladores
     * reduciendo la repeticon de codigo y mejorando la legibilidad
     * 
     * @param bindingResult El objeto {@link BindingResult} que contiene los resultados
     *                      de la validacion realizda por el framework Spring.
     * 
     * @param errorCode     Un codigo de error personalizado que identifica el conexto
     *                      o tipo de validacion que fallo
     * 
     * @throws DataValidationException  Si se detectan errores en la validacion, lanza una
     *                                  una excepcion con el codigo de error proporcionado
     *                                  y un mapa de los errores especificos.
     * 
     * 
     * @see BindingResult
     * @see BindingResultException
     */
    public static void validateBindingResult(BindingResult bindingResult, String errorCode){
        if (bindingResult.hasErrors()) {
            throw new BindingResultException(errorCode, BindingResultHelper.extractErrors(bindingResult));
        }
    }



}
