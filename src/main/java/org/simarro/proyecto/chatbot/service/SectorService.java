package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.SectorEdit;

public interface SectorService {
    SectorEdit create(SectorEdit sectorEdit);
    SectorEdit read(Long id);
    SectorEdit update(Long id, SectorEdit sectorEdit);
    void delete(Long id);

    // List<SectorEdit> getAllSectores();

    PaginaResponse<SectorEdit> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<SectorEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}