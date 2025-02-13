package org.simarro.proyecto.chatbot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreguntaEdit {
    private Long id;
    private String texto;
    private String tipo;
    private Integer cuestionario_id;
}
