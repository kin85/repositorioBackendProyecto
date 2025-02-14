package org.simarro.proyecto.chatbot.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.simarro.proyecto.chatbot.model.db.PreguntaDb;
import org.simarro.proyecto.chatbot.model.db.TipoPreguntaDb;
import org.simarro.proyecto.chatbot.model.db.CuestionarioDb;
import org.simarro.proyecto.chatbot.model.dto.PreguntaEdit;

@Mapper
public interface PreguntaMapper {
    PreguntaMapper INSTANCE = Mappers.getMapper(PreguntaMapper.class);

    @Mapping(source = "cuestionario.id", target = "cuestionarioId")
    @Mapping(source = "tipo.id", target = "tipoId")
    @Mapping(source = "siguienteSi.id", target = "siguienteSiId")
    @Mapping(source = "siguienteNo.id", target = "siguienteNoId")
    PreguntaEdit preguntaDbPreguntaEdit(PreguntaDb preguntaDb);

    @Mapping(source = "cuestionarioId", target = "cuestionario.id")
    @Mapping(source = "tipoId", target = "tipo.id")
    @Mapping(source = "siguienteSiId", target = "siguienteSi.id")
    @Mapping(source = "siguienteNoId", target = "siguienteNo.id")
    PreguntaDb preguntaEditPreguntaDb(PreguntaEdit preguntaEdit);

    void updatePreguntaDbFromPreguntaEdit(PreguntaEdit preguntaEdit, @MappingTarget PreguntaDb preguntaDb);

    // Métodos de mapeo para las relaciones
    default CuestionarioDb mapCuestionarioIdToCuestionario(Long cuestionarioId) {
        if (cuestionarioId == null) {
            return null;
        }
        CuestionarioDb cuestionario = new CuestionarioDb();
        cuestionario.setId(cuestionarioId);
        return cuestionario;
    }

    default TipoPreguntaDb mapTipoIdToTipo(Long tipoId) {
        if (tipoId == null) {
            return null;
        }
        TipoPreguntaDb tipo = new TipoPreguntaDb();
        tipo.setId(tipoId);
        return tipo;
    }

    default PreguntaDb mapSiguienteSiIdToPregunta(Long siguienteSiId) {
        if (siguienteSiId == null) {
            return null;
        }
        PreguntaDb siguienteSi = new PreguntaDb();
        siguienteSi.setId(siguienteSiId);
        return siguienteSi;
    }

    default PreguntaDb mapSiguienteNoIdToPregunta(Long siguienteNoId) {
        if (siguienteNoId == null) {
            return null;
        }
        PreguntaDb siguienteNo = new PreguntaDb();
        siguienteNo.setId(siguienteNoId);
        return siguienteNo;
    }
}
