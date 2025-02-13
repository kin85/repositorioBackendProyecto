package org.simarro.proyecto.chatbot.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.simarro.proyecto.chatbot.model.db.EstadoAcreditacionDb;
import org.simarro.proyecto.chatbot.model.dto.EstadoAcreditacionEdit;

import java.util.List;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.FiltroBusqueda;
import org.springframework.data.domain.Page;

@Mapper
public interface EstadoAcreditacionMapper {
    EstadoAcreditacionMapper INSTANCE = Mappers.getMapper(EstadoAcreditacionMapper.class);

    @Mapping(source = "usuario.id", target = "usuario_id")
    @Mapping(source = "modulo.id", target = "modulo_id")
    EstadoAcreditacionEdit estadoAcreditacionDbEstadoAcreditacionEdit(EstadoAcreditacionDb estadoAcreditacionDb);

    @Mapping(source = "usuario_id", target = "usuario.id")
    @Mapping(source = "modulo_id", target = "modulo.id")
    EstadoAcreditacionDb estadoAcreditacionEditEstadoAcreditacionDb(EstadoAcreditacionEdit estadoAcreditacionEdit);

    void updateEstadoAcreditacionDbFromEstadoAcreditacionEdit(EstadoAcreditacionEdit estadoAcreditacionEdit ,  @MappingTarget EstadoAcreditacionDb estadoAcreditacionDb);

    List<EstadoAcreditacionEdit> estadoAcreditacionDbToEstadoAcreditacionEdit(List<EstadoAcreditacionDb> estadoAcreditacionDb);

    static PaginaResponse<EstadoAcreditacionEdit> pageToPaginaResponseEstadoAcreditacionEdit(
            Page<EstadoAcreditacionDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.estadoAcreditacionDbToEstadoAcreditacionEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}