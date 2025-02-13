package org.simarro.proyecto.chatbot.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.db.SesionDb;
import org.simarro.proyecto.chatbot.model.dto.FiltroBusqueda;
import org.simarro.proyecto.chatbot.model.dto.SesionEdit;
import org.springframework.data.domain.Page;

@Mapper
public interface SesionMapper {
    SesionMapper INSTANCE = Mappers.getMapper(SesionMapper.class);

    SesionEdit sesionDbToSesionEdit(SesionDb sesionDb);
    
    SesionDb sesionEditToSesionDb(SesionEdit sesionEdit);
    
    void updateSesionDbFromSesionEdit(SesionEdit sesionEdit ,  @MappingTarget SesionDb sesionDb);

    List<SesionEdit> sesionDbToSesionEdit(List<SesionDb> sesionDb);

    static PaginaResponse<SesionEdit> pageToPaginaResponseSesionEdit(
            Page<SesionDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.sesionDbToSesionEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}
