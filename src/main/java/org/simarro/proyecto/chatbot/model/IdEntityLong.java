package org.simarro.proyecto.chatbot.model;

import org.simarro.proyecto.chatbot.exception.DataValidationException;

import lombok.Value;

@Value
public class IdEntityLong {
    Long value;

    public IdEntityLong(String id) {
        try {
            this.value = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new DataValidationException("ID_FORMAT_INVALID",
                    " El ID debe ser un valor numérico de tipo Long (INT64).");
        }
    }
}