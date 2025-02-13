package org.simarro.proyecto.chatbot.service.impl;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.filters.utils.PaginationFactory;
import org.simarro.proyecto.chatbot.helper.PeticionListadoFiltradoConverter;
import org.simarro.proyecto.chatbot.model.db.SesionDb;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.SesionEdit;
import org.simarro.proyecto.chatbot.repository.SesionRepository;
import org.simarro.proyecto.chatbot.service.SesionService;
import org.simarro.proyecto.chatbot.service.mapper.SesionMapper;
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
public class SesionServiceImpl implements SesionService {

    private final SesionRepository sesionRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public SesionDb createSesion(SesionDb sesion) {
        return sesionRepository.save(sesion);
    }

    @Override
    public SesionDb getSesionById(long id) {
        return sesionRepository.findById(id).orElse(null);
    }

    // @Override
    // public List<SesionDb> getAllSesiones() {
    //     return sesionRepository.findAll();
    // }

    @Override
    public SesionDb updateSesion(long id, SesionDb sesion) {
        SesionDb existingSesion = sesionRepository.findById(id).orElse(null);
        if (existingSesion != null) {
            existingSesion.setNombre(sesion.getNombre());
            existingSesion.setTelefono(sesion.getTelefono());
            existingSesion.setFechaCreacion(sesion.getFechaCreacion());
            return sesionRepository.save(existingSesion);
        }
        return null;
    }

    @Override
    public void deleteSesion(long id) {
        sesionRepository.deleteById(id);
    }

    @Override
    public PaginaResponse<SesionEdit> findAll(String[] filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<SesionEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<SesionDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<SesionDb> page = sesionRepository.findAll(filtrosBusquedaSpecification, pageable);
            return SesionMapper.pageToPaginaResponseSesionEdit(
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