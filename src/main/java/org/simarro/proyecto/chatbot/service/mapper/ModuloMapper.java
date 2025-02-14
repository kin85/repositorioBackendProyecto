package org.simarro.proyecto.chatbot.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.simarro.proyecto.chatbot.model.db.ModuloDb;
import org.simarro.proyecto.chatbot.model.dto.ModuloEdit;
import java.util.List;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.FiltroBusqueda;
import org.springframework.data.domain.Page;

@Mapper
public interface ModuloMapper {
    ModuloMapper INSTANCE = Mappers.getMapper(ModuloMapper.class);

    @Mapping(target = "sector_id", source = "sector.id")
    ModuloEdit moduloDbModuloEdit(ModuloDb moduloDb);
    
    @Mapping(target = "sector.id", source = "sector_id")
    ModuloDb moduloEditModuloDb(ModuloEdit moduloEdit);
    
    void updateModuloDbFromModuloEdit(ModuloEdit moduloEdit ,  @MappingTarget ModuloDb moduloDb);

    List<ModuloEdit> moduloDbToModuloEdit(List<ModuloDb> moduloDb);

    static PaginaResponse<ModuloEdit> pageToPaginaResponseModuloEdit(
            Page<ModuloDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.moduloDbToModuloEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}
