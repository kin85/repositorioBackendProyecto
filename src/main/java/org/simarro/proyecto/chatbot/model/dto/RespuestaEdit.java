package org.simarro.proyecto.chatbot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RespuestaEdit {
    private Long id;
    private String respuesta;
    private Integer pregunta_id;
    private Integer usuario_id;
}