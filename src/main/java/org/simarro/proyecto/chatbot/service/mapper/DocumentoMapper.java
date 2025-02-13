package org.simarro.proyecto.chatbot.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.db.DocumentoDb;
import org.simarro.proyecto.chatbot.model.dto.DocumentoEdit;
import org.simarro.proyecto.chatbot.model.dto.FiltroBusqueda;
import org.springframework.data.domain.Page;

@Mapper
public interface DocumentoMapper {
    DocumentoMapper INSTANCE = Mappers.getMapper(DocumentoMapper.class);

    @Mapping(source = "usuario.id", target = "usuario_id")
    DocumentoEdit documentoDbToDocumentoEdit(DocumentoDb documentoDb);
    
    @Mapping(source = "usuario_id", target = "usuario.id")
    DocumentoDb documentoEditToDocumentoDb(DocumentoEdit documentoEdit);
    
    void updateDocumentoDbFromDocumentoEdit(DocumentoEdit documentoEdit ,  @MappingTarget DocumentoDb documentoDb);

    List<DocumentoEdit> documentoDbToDocumentoEdit(List<DocumentoDb> documentoDb);

    static PaginaResponse<DocumentoEdit> pageToPaginaResponseDocumentoEdit(
            Page<DocumentoDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.documentoDbToDocumentoEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}
