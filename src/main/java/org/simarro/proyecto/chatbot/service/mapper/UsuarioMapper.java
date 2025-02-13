package org.simarro.proyecto.chatbot.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.db.UsuarioDb;
import org.simarro.proyecto.chatbot.model.dto.FiltroBusqueda;
import org.simarro.proyecto.chatbot.model.dto.UsuarioInfo;
import org.springframework.data.domain.Page;

@Mapper
public interface UsuarioMapper { 
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
    UsuarioInfo usuarioDbToUsuarioInfo(UsuarioDb usuarioDb);
    List<UsuarioInfo> usuarioDbToUsuarioInfo(List<UsuarioDb> usuarioDb);

    static PaginaResponse<UsuarioInfo> pageToPaginaResponseUsuarioInfo(
            Page<UsuarioDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.usuarioDbToUsuarioInfo(page.getContent()),
                filtros,
                ordenaciones);
    }
}
