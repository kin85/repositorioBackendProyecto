package org.simarro.proyecto.chatbot.repository;

import org.simarro.proyecto.chatbot.model.db.EstadoAcreditacionDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoAcreditacionRepository extends JpaRepository<EstadoAcreditacionDb, Long>, JpaSpecificationExecutor<EstadoAcreditacionDb> {
}