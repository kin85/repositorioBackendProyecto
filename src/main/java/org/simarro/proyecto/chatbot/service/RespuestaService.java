package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.RespuestaEdit;

public interface RespuestaService {
    RespuestaEdit create(RespuestaEdit rEditespuestaEdit);
    RespuestaEdit read(Long id);
    void delete(Long id);

    // List<RespuestaEdit> getAllRespuestas();

    PaginaResponse<RespuestaEdit> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<RespuestaEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}