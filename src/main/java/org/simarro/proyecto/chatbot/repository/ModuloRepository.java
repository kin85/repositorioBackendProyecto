package org.simarro.proyecto.chatbot.repository;

import java.util.List;

import org.simarro.proyecto.chatbot.model.db.ModuloDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloRepository extends JpaRepository<ModuloDb, Long>, JpaSpecificationExecutor<ModuloDb> {
    List<ModuloDb> findBySectorId(Long sectorId);
}