package org.simarro.proyecto.chatbot.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuestionarioEdit {
    private Long id;
    private String titulo;
    private String unidad_competencia_id;
}