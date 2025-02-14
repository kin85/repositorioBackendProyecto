package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.MensajeEdit;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;

public interface MensajeService {
    MensajeEdit create(MensajeEdit mensajeEdit);
    MensajeEdit read(Long id);
    MensajeEdit update(Long id, MensajeEdit mensajeEdit);
    void delete(Long id);
    
    PaginaResponse<MensajeEdit> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<MensajeEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;

    List<MensajeEdit> getMensajesByUsuarioId(Long usuarioId);
    List<MensajeEdit> getMensajesByAcreditacionId(Long acreditacionId);
}
