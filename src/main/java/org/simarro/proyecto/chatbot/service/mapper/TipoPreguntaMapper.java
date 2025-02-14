package org.simarro.proyecto.chatbot.service.mapper;

import org.mapstruct.Mapper;
import org.simarro.proyecto.chatbot.model.db.TipoPreguntaDb;
import org.simarro.proyecto.chatbot.model.dto.TipoPreguntaEdit;

@Mapper
public interface TipoPreguntaMapper {
    TipoPreguntaMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TipoPreguntaMapper.class);

    TipoPreguntaEdit tipoPreguntaDbToTipoPreguntaEdit(TipoPreguntaDb tipoPreguntaDb);

    TipoPreguntaDb tipoPreguntaDtoToTipoPreguntaDb(TipoPreguntaEdit tipoPreguntaDto);
}
