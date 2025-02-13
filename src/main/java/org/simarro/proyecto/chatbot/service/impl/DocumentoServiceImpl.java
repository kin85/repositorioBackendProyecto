package org.simarro.proyecto.chatbot.service.impl;

import java.util.List;

import org.simarro.proyecto.chatbot.exception.EntityIllegalArgumentException;
import org.simarro.proyecto.chatbot.exception.EntityNotFoundException;
import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.filters.utils.PaginationFactory;
import org.simarro.proyecto.chatbot.helper.PeticionListadoFiltradoConverter;
import org.simarro.proyecto.chatbot.model.db.DocumentoDb;
import org.simarro.proyecto.chatbot.model.dto.DocumentoEdit;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.repository.DocumentoRepository;
import org.simarro.proyecto.chatbot.service.DocumentoService;
import org.simarro.proyecto.chatbot.service.mapper.DocumentoMapper;
import org.simarro.proyecto.chatbot.service.specification.FiltroBusquedaSpecification;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public DocumentoEdit create(DocumentoEdit documentoEdit) {
        if (documentoEdit.getId() !=  null) {
            throw new EntityIllegalArgumentException("DOCUMENTO_ID_MISMATCH", "El ID debe ser nulo al crear un nuevo documento");
        }
        DocumentoDb entity = DocumentoMapper.INSTANCE.documentoEditToDocumentoDb(documentoEdit);
        return DocumentoMapper.INSTANCE.documentoDbToDocumentoEdit(documentoRepository.save(entity));
    }

    @Override
    public DocumentoEdit read(long id) {
        DocumentoDb entity = documentoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("DOC_NOT_FOUND", "No se encontró el documento con id: " + id));
        return DocumentoMapper.INSTANCE.documentoDbToDocumentoEdit(entity);
    }

    @Override
    public DocumentoEdit update(long id, DocumentoEdit documentoEdit) {

        if (id != (documentoEdit.getId())) {
            throw new EntityIllegalArgumentException("DOCUMENTO_ID_MISMATCH", "El ID proporcionado no coincide con el ID del documento a actualizar.");
        }

        DocumentoDb existingEntity = documentoRepository.findById(id)
                                                           .orElseThrow(() -> new EntityNotFoundException("DOCUMENTO_NOT_FOUND", "Documento no encontrado con id " + documentoEdit.getId()));
        
        DocumentoMapper.INSTANCE.updateDocumentoDbFromDocumentoEdit(documentoEdit, existingEntity);
        return DocumentoMapper.INSTANCE.documentoDbToDocumentoEdit(documentoRepository.save(existingEntity));
    }

    @Override
    public void delete(long id) {
        documentoRepository.deleteById(id);
    }

    @Override
    public PaginaResponse<DocumentoEdit> findAll(String[] filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<DocumentoEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<DocumentoDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<DocumentoDb> page = documentoRepository.findAll(filtrosBusquedaSpecification, pageable);
            return DocumentoMapper.pageToPaginaResponseDocumentoEdit(
                page,
                peticionListadoFiltrado.getListaFiltros(), 
                peticionListadoFiltrado.getSort());
        } catch (JpaSystemException e) {
            @SuppressWarnings("null")
            String cause = e.getRootCause() != null ? e.getRootCause().getMessage() : "";
            throw new FiltroException("BAD_OPERATOR_FILTER",
                    "Error: No se puede realizar esa operación sobre el atributo por el tipo de dato", e.getMessage() + ":" + cause);
        } catch (PropertyReferenceException e) {
            throw new FiltroException("BAD_ATTRIBUTE_ORDER",
                    "Error: No existe el nombre del atributo de ordenación en la tabla", e.getMessage());
        } catch (InvalidDataAccessApiUsageException e) {
            throw new FiltroException("BAD_ATTRIBUTE_FILTER", "Error: Posiblemente no existe el atributo en la tabla",
                    e.getMessage());
        }
    }
}