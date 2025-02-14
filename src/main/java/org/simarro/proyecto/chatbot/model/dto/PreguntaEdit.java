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
    private Long tipoId; 
    private Long cuestionarioId;
    private Long siguienteSiId; 
    private Long siguienteNoId;  
    private boolean finalSi;
    private boolean finalNo;
    private String explicacionSi;
    private String explicacionNo;
    private int orden;
}
