package org.simarro.proyecto.chatbot.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "estado_acreditacion")
public class EstadoAcreditacionDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String estado;
    @Column(name = "fecha_actualizacion")
    private java.sql.Timestamp fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioDb usuario;

    @OneToOne
    @JoinColumn(name = "modulo_id")
    private ModuloDb modulo;
}