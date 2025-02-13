package org.simarro.proyecto.chatbot.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.simarro.proyecto.chatbot.exception.EntityIllegalArgumentException;
import org.simarro.proyecto.chatbot.exception.EntityNotFoundException;
import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.filters.utils.PaginationFactory;
import org.simarro.proyecto.chatbot.helper.PeticionListadoFiltradoConverter;
import org.simarro.proyecto.chatbot.model.db.PreguntaDb;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.PreguntaEdit;
import org.simarro.proyecto.chatbot.repository.PreguntaRepository;
import org.simarro.proyecto.chatbot.service.PreguntaService;
import org.simarro.proyecto.chatbot.service.mapper.PreguntaMapper;
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
public class PreguntaServiceImpl implements PreguntaService {

    private final PreguntaRepository preguntaRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public PreguntaEdit create(PreguntaEdit preguntaEdit) {
        if (preguntaEdit.getId() !=  null) {
            throw new EntityIllegalArgumentException("PREGUNTA_ID_MISMATCH", "El ID debe ser nulo al crear una nueva pregunta");
        }

        PreguntaDb entity = PreguntaMapper.INSTANCE.preguntaEditPreguntaDb(preguntaEdit);
        return PreguntaMapper.INSTANCE.preguntaDbPreguntaEdit(preguntaRepository.save(entity));
    }

    @Override
    public PreguntaEdit read(Long id) {
        PreguntaDb entity = preguntaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("PREGUNTA_NOT_FOUND", "No se encontró la pregunta con id: " + id));
        return PreguntaMapper.INSTANCE.preguntaDbPreguntaEdit(entity);
    }

    @Override
    public PreguntaEdit update(Long id, PreguntaEdit preguntaEdit) {
        if (!id.equals(preguntaEdit.getId())) {
            throw new EntityIllegalArgumentException("CUESTIONARIO_ID_MISMATCH", "El ID proporcionado no coincide con el ID del cuestionario a actualizar.");
        }

        PreguntaDb existingEntity = preguntaRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("CUESTIONARIO_NOT_FOUND_FOR_UPDATE", "No se puede actualizar. El cuestionario con ID " + id + " no existe."));
        
        PreguntaMapper.INSTANCE.updatePreguntaDbFromPreguntaEdit(preguntaEdit, existingEntity);
        return PreguntaMapper.INSTANCE.preguntaDbPreguntaEdit(preguntaRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (preguntaRepository.existsById(id)) {
            preguntaRepository.deleteById(id);
        }
    }

    // @Override
    // public List<PreguntaEdit> getAllPreguntas() {
    //     return preguntaRepository.findAll().stream()
    //         .map(PreguntaMapper.INSTANCE::preguntaDbPreguntaEdit)
    //         .collect(Collectors.toList());
    // }

    @Override
    public List<PreguntaEdit> obtenerPreguntasPorCuestionario(Long cuestionarioId) {
        return preguntaRepository.findByCuestionarioId(cuestionarioId).stream()
            .map(PreguntaMapper.INSTANCE::preguntaDbPreguntaEdit)
            .collect(Collectors.toList());
    }

    @Override
    public PaginaResponse<PreguntaEdit> findAll(String[] filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<PreguntaEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<PreguntaDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<PreguntaDb> page = preguntaRepository.findAll(filtrosBusquedaSpecification, pageable);
            return PreguntaMapper.pageToPaginaResponsePreguntaEdit(
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