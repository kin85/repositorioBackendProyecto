package org.simarro.proyecto.chatbot.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstadoAcreditacionEdit {
    private Long id;
    private String estado;
    private LocalDateTime fecha_actualizacion = LocalDateTime.now();
    private Integer usuario_id;
    private Integer modulo_id;
    private Integer asesor_id;
}