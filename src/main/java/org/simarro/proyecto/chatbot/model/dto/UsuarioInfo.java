package org.simarro.proyecto.chatbot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioInfo {
    private long id;
    private String nombre;
    private String nickname;
    private String email;
    private String telefono;
    private java.sql.Date fechaNacimiento;
    private String estado;
}
