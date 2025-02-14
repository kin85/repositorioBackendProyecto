package org.simarro.proyecto.chatbot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModuloEdit {
    private Long id;
    private String nombre;
    private Integer nivel;
    private Integer sector_id;
}
