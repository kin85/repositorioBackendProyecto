package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.model.dto.PreguntaEdit;

public interface PreguntaService {
    PreguntaEdit create(PreguntaEdit preguntaEdit);
    PreguntaEdit read(Long id);
    PreguntaEdit update(Long id, PreguntaEdit preguntaEdit);
    void delete(Long id);
    List<PreguntaEdit> getAllPreguntas();
    List<PreguntaEdit> obtenerPreguntasPorCuestionario(Long cuestionarioId);
}
