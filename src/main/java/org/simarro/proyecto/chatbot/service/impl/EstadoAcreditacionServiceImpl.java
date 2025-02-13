package org.simarro.proyecto.chatbot.service.impl;


import java.util.List;

import org.simarro.proyecto.chatbot.exception.EntityIllegalArgumentException;
import org.simarro.proyecto.chatbot.exception.EntityNotFoundException;
import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.filters.utils.PaginationFactory;
import org.simarro.proyecto.chatbot.helper.PeticionListadoFiltradoConverter;
import org.simarro.proyecto.chatbot.model.db.EstadoAcreditacionDb;
import org.simarro.proyecto.chatbot.model.dto.EstadoAcreditacionEdit;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.repository.EstadoAcreditacionRepository;
import org.simarro.proyecto.chatbot.service.EstadoAcreditacionService;
import org.simarro.proyecto.chatbot.service.mapper.EstadoAcreditacionMapper;
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
public class EstadoAcreditacionServiceImpl implements EstadoAcreditacionService {

    private final EstadoAcreditacionRepository estadoAcreditacionRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public EstadoAcreditacionEdit create(EstadoAcreditacionEdit estadoAcreditacionEdit) {
        if (estadoAcreditacionEdit.getId() !=  null) {
            throw new EntityIllegalArgumentException("ESTADO_ACREDITACION_ID_MISMATCH", "El ID debe ser nulo al crear un nuevo estado de acreditación");
        }

        EstadoAcreditacionDb entity = EstadoAcreditacionMapper.INSTANCE.estadoAcreditacionEditEstadoAcreditacionDb(estadoAcreditacionEdit);
        return EstadoAcreditacionMapper.INSTANCE.estadoAcreditacionDbEstadoAcreditacionEdit(estadoAcreditacionRepository.save(entity));
    }

    @Override
    public EstadoAcreditacionEdit read(Long id) {
        EstadoAcreditacionDb entity = estadoAcreditacionRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("ESTADO_ACREDITACION_NOT_FOUND", "No se encontró el estado de acreditación con id: " + id));
        return EstadoAcreditacionMapper.INSTANCE.estadoAcreditacionDbEstadoAcreditacionEdit(entity);
    }

    @Override
    public EstadoAcreditacionEdit update(Long id, EstadoAcreditacionEdit estadoAcreditacionEdit) {
        if (!id.equals(estadoAcreditacionEdit.getId())) {
            throw new EntityIllegalArgumentException("ESTADO_ACREDITACION_ID_MISMATCH", "El ID proporcionado no coincide con el ID del estado de acreditacion a actualizar.");
        }

        EstadoAcreditacionDb existingEntity = estadoAcreditacionRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("ESTADO_ACREDITACION_NOT_FOUND_FOR_UPDATE", "No se puede actualizar. El estado de acreditación con ID " + id + " no existe."));
        
        EstadoAcreditacionMapper.INSTANCE.updateEstadoAcreditacionDbFromEstadoAcreditacionEdit(estadoAcreditacionEdit, existingEntity);
        return EstadoAcreditacionMapper.INSTANCE.estadoAcreditacionDbEstadoAcreditacionEdit(estadoAcreditacionRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (estadoAcreditacionRepository.existsById(id)) {
            estadoAcreditacionRepository.deleteById(id);
        }
    }

    @Override
    public PaginaResponse<EstadoAcreditacionEdit> findAll(String[] filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<EstadoAcreditacionEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<EstadoAcreditacionDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<EstadoAcreditacionDb> page = estadoAcreditacionRepository.findAll(filtrosBusquedaSpecification, pageable);
            return EstadoAcreditacionMapper.pageToPaginaResponseEstadoAcreditacionEdit(
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