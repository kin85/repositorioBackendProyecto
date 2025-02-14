package org.simarro.proyecto.chatbot.repository;

import java.util.List;

import org.simarro.proyecto.chatbot.model.db.UnidadCompetenciaDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadCompetenciaRepository extends JpaRepository<UnidadCompetenciaDb, String>, JpaSpecificationExecutor<UnidadCompetenciaDb> {
    // Optional<UnidadCompetenciaDb> findByNombreIgnoreCase(String nombre);
    List<UnidadCompetenciaDb> findByModuloId(Long moduloId);
}
