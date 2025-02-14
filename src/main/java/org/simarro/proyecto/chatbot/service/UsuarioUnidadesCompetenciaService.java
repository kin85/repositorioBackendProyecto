package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.UsuarioUnidadesCompetenciaEdit;

public interface UsuarioUnidadesCompetenciaService {
    
    UsuarioUnidadesCompetenciaEdit create(UsuarioUnidadesCompetenciaEdit usuarioUnidadesCompetenciaEdit);
    UsuarioUnidadesCompetenciaEdit read(Long id);
    UsuarioUnidadesCompetenciaEdit update(Long id, UsuarioUnidadesCompetenciaEdit usuarioUnidadesCompetenciaEdit);
    void delete(Long id);

    // List<UsuarioUnidadesCompetenciaEdit> getAllUsuarioUnidadCompetencias();
    
    PaginaResponse<UsuarioUnidadesCompetenciaEdit> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<UsuarioUnidadesCompetenciaEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
    
}