package org.simarro.proyecto.chatbot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioUnidadesCompetenciaEdit{
    private Long id;
    private Integer usuario_id;
    private String unidad_competencia_id;
    private String estado;
}
