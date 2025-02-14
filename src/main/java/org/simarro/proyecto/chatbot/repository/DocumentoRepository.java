package org.simarro.proyecto.chatbot.repository;

import org.simarro.proyecto.chatbot.model.db.DocumentoDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<DocumentoDb, Long>, JpaSpecificationExecutor<DocumentoDb> {
}