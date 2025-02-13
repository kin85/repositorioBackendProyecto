package org.simarro.proyecto.chatbot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SesionEdit {
    private long id;
    private String nombre;
    private String telefono;
    private java.sql.Timestamp fechaCreacion;
}