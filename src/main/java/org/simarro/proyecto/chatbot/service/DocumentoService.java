package org.simarro.proyecto.chatbot.service;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.DocumentoEdit;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;

public interface DocumentoService {
    DocumentoEdit create(DocumentoEdit documento);
    DocumentoEdit read(long id);
    DocumentoEdit update(long id, DocumentoEdit documento);
    void delete(long id);

    PaginaResponse<DocumentoEdit> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<DocumentoEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}
