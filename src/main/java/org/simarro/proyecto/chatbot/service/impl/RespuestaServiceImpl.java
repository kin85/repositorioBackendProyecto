package org.simarro.proyecto.chatbot.service.impl;


import java.util.List;

import org.simarro.proyecto.chatbot.exception.EntityIllegalArgumentException;
import org.simarro.proyecto.chatbot.exception.EntityNotFoundException;
import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.filters.utils.PaginationFactory;
import org.simarro.proyecto.chatbot.helper.PeticionListadoFiltradoConverter;
import org.simarro.proyecto.chatbot.model.db.RespuestaDb;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.RespuestaEdit;
import org.simarro.proyecto.chatbot.repository.RespuestaRepository;
import org.simarro.proyecto.chatbot.service.RespuestaService;
import org.simarro.proyecto.chatbot.service.mapper.RespuestaMapper;
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
public class RespuestaServiceImpl implements RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public RespuestaEdit create(RespuestaEdit respuestaEdit) {
        if (respuestaEdit.getId() !=  null) {
            throw new EntityIllegalArgumentException("RESPUESTA_ID_MISMATCH", "El ID debe ser nulo al crear una nueva respuesta");
        }

        RespuestaDb entity = RespuestaMapper.INSTANCE.respuestaEditRespuestaDb(respuestaEdit);
        return RespuestaMapper.INSTANCE.respuestaDbRespuestaEdit(respuestaRepository.save(entity));
    }

    @Override
    public RespuestaEdit read(Long id) {
        RespuestaDb entity = respuestaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("RESPUESTA_NOT_FOUND", "No se encontró la respuesta id: " + id));
        return RespuestaMapper.INSTANCE.respuestaDbRespuestaEdit(entity);
    }


    @Override
    public void delete(Long id) {
        if (respuestaRepository.existsById(id)) {
            respuestaRepository.deleteById(id);
        }
    }

    // @Override
    // public List<RespuestaEdit> getAllRespuestas() {
    //     return respuestaRepository.findAll().stream()
    //         .map(RespuestaMapper.INSTANCE::respuestaDbRespuestaEdit)
    //         .collect(Collectors.toList());
    // }

    @Override
    public PaginaResponse<RespuestaEdit> findAll(String[] filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<RespuestaEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<RespuestaDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<RespuestaDb> page = respuestaRepository.findAll(filtrosBusquedaSpecification, pageable);
            return RespuestaMapper.pageToPaginaResponseRespuestaEdit(
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