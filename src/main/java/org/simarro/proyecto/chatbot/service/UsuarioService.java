package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.db.UsuarioDb;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.UsuarioInfo;

public interface UsuarioService {
    UsuarioInfo createUsuario(UsuarioDb usuario);
    UsuarioInfo getUsuarioById(long id);
    UsuarioDb updateUsuario(Long id, UsuarioDb usuario);
    void deleteUsuario(Long id);
    PaginaResponse<UsuarioInfo> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<UsuarioInfo> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}