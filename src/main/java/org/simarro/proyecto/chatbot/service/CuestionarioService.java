package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.CuestionarioEdit;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;

public interface CuestionarioService {
    CuestionarioEdit create(CuestionarioEdit cuestionarioEdit);
    CuestionarioEdit read(Long id);
    CuestionarioEdit update(Long id, CuestionarioEdit cuestionarioEdit);
    void delete(Long id);
    
    PaginaResponse<CuestionarioEdit> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<CuestionarioEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}