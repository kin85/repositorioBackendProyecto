package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.PreguntaEdit;

public interface PreguntaService {
    PreguntaEdit create(PreguntaEdit preguntaEdit);
    PreguntaEdit read(Long id);
    PreguntaEdit update(Long id, PreguntaEdit preguntaEdit);
    void delete(Long id);
    // List<PreguntaEdit> getAllPreguntas();
    List<PreguntaEdit> obtenerPreguntasPorCuestionario(Long cuestionarioId);

    PaginaResponse<PreguntaEdit> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<PreguntaEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}
