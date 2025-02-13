package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.db.SesionDb;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.SesionEdit;

public interface SesionService {
    SesionDb createSesion(SesionDb sesion);
    SesionDb getSesionById(long id);
    // List<SesionDb> getAllSesiones();
    SesionDb updateSesion(long id, SesionDb sesion);
    void deleteSesion(long id);

    PaginaResponse<SesionEdit> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<SesionEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}
