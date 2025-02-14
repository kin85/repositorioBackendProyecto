package org.simarro.proyecto.chatbot.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.db.SectorDb;
import org.simarro.proyecto.chatbot.model.dto.FiltroBusqueda;
import org.simarro.proyecto.chatbot.model.dto.SectorEdit;
import org.springframework.data.domain.Page;

@Mapper
public interface SectorMapper {
    SectorMapper INSTANCE = Mappers.getMapper(SectorMapper.class);

    SectorEdit sectorDbSectorEdit(SectorDb sectorDb);
    SectorDb sectorEditSectorDb(SectorEdit sectorEdit);
    void updateSectorDbFromSectorEdit(SectorEdit sectorEdit ,  @MappingTarget SectorDb sectorDb);

    List<SectorEdit> sectorDbToSectorEdit(List<SectorDb> sectorDb);

    static PaginaResponse<SectorEdit> pageToPaginaResponseSectorEdit(
            Page<SectorDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.sectorDbToSectorEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}
