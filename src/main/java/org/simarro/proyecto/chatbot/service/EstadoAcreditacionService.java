package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.EstadoAcreditacionEdit;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;

public interface EstadoAcreditacionService {
    public EstadoAcreditacionEdit create(EstadoAcreditacionEdit estadoAcreditacionEdit);
    public EstadoAcreditacionEdit read(Long id);
    public EstadoAcreditacionEdit update(Long id, EstadoAcreditacionEdit estadoAcreditacionEdit);
    public void delete(Long id);

    PaginaResponse<EstadoAcreditacionEdit> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<EstadoAcreditacionEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}
