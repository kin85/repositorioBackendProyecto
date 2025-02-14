package org.simarro.proyecto.chatbot.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.db.MensajeDb;
import org.simarro.proyecto.chatbot.model.dto.MensajeEdit;
import org.simarro.proyecto.chatbot.model.dto.FiltroBusqueda;
import org.springframework.data.domain.Page;

@Mapper
public interface MensajeMapper {
    MensajeMapper INSTANCE = Mappers.getMapper(MensajeMapper.class);

    @Mapping(source = "usuario.id", target = "usuario_id")
    @Mapping(source = "acreditacion.id", target = "acreditacion_id")
    MensajeEdit mensajeDbToMensajeEdit(MensajeDb mensajeDb);
    
    @Mapping(source = "usuario_id", target = "usuario.id")
    @Mapping(source = "acreditacion_id", target = "acreditacion.id")
    MensajeDb mensajeEditToMensajeDb(MensajeEdit mensajeEdit);
    
    void updateMensajeDbFromMensajeEdit(MensajeEdit mensajeEdit ,  @MappingTarget MensajeDb mensajeDb);

    @Mapping(source = "usuario_id", target = "usuario.id")
    @Mapping(source = "acreditacion_id", target = "acreditacion.id")
    List<MensajeEdit> mensajeDbToMensajeEdit(List<MensajeDb> mensajeDb);

    static PaginaResponse<MensajeEdit> pageToPaginaResponseMensajeEdit(
            Page<MensajeDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.mensajeDbToMensajeEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}
