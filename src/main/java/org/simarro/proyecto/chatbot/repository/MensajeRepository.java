package org.simarro.proyecto.chatbot.repository;

import java.util.List;

import org.simarro.proyecto.chatbot.model.db.MensajeDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<MensajeDb, Long>, JpaSpecificationExecutor<MensajeDb> {
    List<MensajeDb> findByUsuarioId(Long usuarioId);
    List<MensajeDb> findByAcreditacionId(Long acreditacionId);
}
