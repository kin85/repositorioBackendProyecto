package org.simarro.proyecto.chatbot.repository;

import java.util.List;

import org.simarro.proyecto.chatbot.model.db.PreguntaDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaRepository extends JpaRepository<PreguntaDb, Long>, JpaSpecificationExecutor<PreguntaDb> {
    List<PreguntaDb> findByCuestionarioId(Long cuestionarioId);
}