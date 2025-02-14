package org.simarro.proyecto.chatbot.repository;

import org.simarro.proyecto.chatbot.model.db.SectorDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<SectorDb, Long>, JpaSpecificationExecutor<SectorDb> {
}
