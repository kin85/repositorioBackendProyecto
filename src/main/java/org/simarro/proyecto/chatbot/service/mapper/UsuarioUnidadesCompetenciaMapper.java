package org.simarro.proyecto.chatbot.service.mapper;

import java.util.List;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.dto.FiltroBusqueda;
import org.springframework.data.domain.Page;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.simarro.proyecto.chatbot.model.db.UsuarioUnidadesCompetenciaDb;
import org.simarro.proyecto.chatbot.model.dto.UsuarioUnidadesCompetenciaEdit;

@Mapper
public interface UsuarioUnidadesCompetenciaMapper {
    UsuarioUnidadesCompetenciaMapper INSTANCE = Mappers.getMapper(UsuarioUnidadesCompetenciaMapper.class);

    UsuarioUnidadesCompetenciaDb usuarioUnidadesCompetenciaDbUsuarioUnidadesCompetenciaEdit(UsuarioUnidadesCompetenciaEdit usuarioUnidadesCompetenciaDb);

    UsuarioUnidadesCompetenciaEdit usuarioUnidadesCompetenciaEditUsuarioUnidadesCompetenciaDb(UsuarioUnidadesCompetenciaDb usuarioUnidadesCompetencia);

    List<UsuarioUnidadesCompetenciaEdit> usuarioUnidadesCompetenciaDbListToUsuarioUnidadesCompetenciaEditList(List<UsuarioUnidadesCompetenciaDb> usuarioUnidadesCompetenciaDbList);

    void updateUsuarioUnidadesCompetenciaDbFromUsuarioUnidadesCompetencia(UsuarioUnidadesCompetenciaEdit usuarioUnidadesCompetencia ,  @MappingTarget UsuarioUnidadesCompetenciaDb usuarioUnidadesCompetenciaDb);

    List<UsuarioUnidadesCompetenciaEdit> usuarioUnidadesCompetenciaDbToUsuarioUnidadesCompetenciaEdit(List<UsuarioUnidadesCompetenciaDb> usuarioUnidadesCompetenciaDb);

    static PaginaResponse<UsuarioUnidadesCompetenciaEdit> pageToPaginaResponseUsuarioUnidadesCompetenciaEdit(
            Page<UsuarioUnidadesCompetenciaDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.usuarioUnidadesCompetenciaDbToUsuarioUnidadesCompetenciaEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}
