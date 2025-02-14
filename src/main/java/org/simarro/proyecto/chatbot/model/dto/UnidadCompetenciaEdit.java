package org.simarro.proyecto.chatbot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadCompetenciaEdit {
    private String id;
    private String nombre;
    private int modulo_id;
}
