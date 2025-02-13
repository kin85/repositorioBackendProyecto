package org.simarro.proyecto.chatbot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MensajeEdit {
    private Long id;
    private Long acreditacion_id;
    private Long usuario_id;
    private String contenido;
    private java.sql.Timestamp fecha;
}