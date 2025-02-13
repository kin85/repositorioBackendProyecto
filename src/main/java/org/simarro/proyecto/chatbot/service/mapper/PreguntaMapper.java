package org.simarro.proyecto.chatbot.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.simarro.proyecto.chatbot.model.db.PreguntaDb;
import org.simarro.proyecto.chatbot.model.dto.PreguntaEdit;
import java.util.List;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.FiltroBusqueda;
import org.springframework.data.domain.Page;

@Mapper
public interface PreguntaMapper {
    PreguntaMapper INSTANCE = Mappers.getMapper(PreguntaMapper.class);

    @Mapping(source = "cuestionario.id", target = "cuestionario_id")
    PreguntaEdit preguntaDbPreguntaEdit(PreguntaDb preguntaDb);
    
    @Mapping(source = "cuestionario_id", target = "cuestionario.id")
    PreguntaDb preguntaEditPreguntaDb(PreguntaEdit preguntaEdit);
    
    void updatePreguntaDbFromPreguntaEdit(PreguntaEdit preguntaEdit ,  @MappingTarget PreguntaDb preguntaDb);

    //@Mapping(source = "cuestionario.id", target = "cuestionario_id")
    List<PreguntaEdit> preguntaDbToPreguntaEdit(List<PreguntaDb> preguntaDb);

    static PaginaResponse<PreguntaEdit> pageToPaginaResponsePreguntaEdit(
            Page<PreguntaDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.preguntaDbToPreguntaEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}
