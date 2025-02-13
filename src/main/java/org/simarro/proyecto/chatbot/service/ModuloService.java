package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.ModuloEdit;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;

public interface ModuloService {
    ModuloEdit create(ModuloEdit moduloEdit);
    ModuloEdit read(Long id);
    ModuloEdit update(Long id, ModuloEdit moduloEdit);
    void delete(Long id);

    // List<ModuloEdit> getAllModulos();
    List<ModuloEdit> getModulosBySector(Long sectorId);

    PaginaResponse<ModuloEdit> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<ModuloEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}