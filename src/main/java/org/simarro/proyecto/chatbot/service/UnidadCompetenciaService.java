package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.UnidadCompetenciaEdit;

public interface UnidadCompetenciaService {
    UnidadCompetenciaEdit create(UnidadCompetenciaEdit unidadCompetenciaEdit);
    UnidadCompetenciaEdit read(String id);
    UnidadCompetenciaEdit update(String id, UnidadCompetenciaEdit unidadCompetenciaEdit);
    void delete(String id);

    // List<UnidadCompetenciaEdit> getAllUnidadCompetencias();
    List<UnidadCompetenciaEdit> getUnidadesCompetenciaByModulo(Long moduloId);

    PaginaResponse<UnidadCompetenciaEdit> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<UnidadCompetenciaEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}
