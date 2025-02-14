package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.model.db.TipoPreguntaDb;

public interface TipoPreguntaService {
    
    TipoPreguntaDb create(TipoPreguntaDb tipoPreguntaDb);
    TipoPreguntaDb read(Long id);
    TipoPreguntaDb update(Long id, TipoPreguntaDb tipoPreguntaDb);
    void delete(Long id);
    List<TipoPreguntaDb> getAllTipoPreguntas();
    
}