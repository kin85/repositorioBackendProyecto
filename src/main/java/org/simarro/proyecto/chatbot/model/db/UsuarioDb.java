package org.simarro.proyecto.chatbot.model.db;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuarios")
public class UsuarioDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    
    private String nickname;

    private String email;

    private String password;

    @Column(name = "password_salt")
    private String passwordSalt;

    private String telefono;

    @Column(name = "fecha_nacimiento")
    private java.sql.Date fechaNacimiento;

    private String estado;

    @Column(name = "fecha_creacion")
    private java.sql.Timestamp fechaCreacion;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_unidadescompetencia",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "unidadcompetencia_id"))
    private List<UnidadCompetenciaDb> unidadesCompetencia;
}