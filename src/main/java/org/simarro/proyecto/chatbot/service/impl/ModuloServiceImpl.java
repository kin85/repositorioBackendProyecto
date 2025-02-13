package org.simarro.proyecto.chatbot.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.simarro.proyecto.chatbot.exception.EntityIllegalArgumentException;
import org.simarro.proyecto.chatbot.exception.EntityNotFoundException;
import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.filters.utils.PaginationFactory;
import org.simarro.proyecto.chatbot.helper.PeticionListadoFiltradoConverter;
import org.simarro.proyecto.chatbot.model.db.ModuloDb;
import org.simarro.proyecto.chatbot.model.dto.ModuloEdit;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.repository.ModuloRepository;
import org.simarro.proyecto.chatbot.service.ModuloService;
import org.simarro.proyecto.chatbot.service.mapper.ModuloMapper;
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
public class ModuloServiceImpl implements ModuloService {

    private final ModuloRepository moduloRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public ModuloEdit create(ModuloEdit moduloEdit) {
        if (moduloEdit.getId() !=  null) {
            throw new EntityIllegalArgumentException("MODULO_ID_MISMATCH", "El ID debe ser nulo al crear un nuevo modulo");
        }

        ModuloDb entity = ModuloMapper.INSTANCE.moduloEditModuloDb(moduloEdit);
        return ModuloMapper.INSTANCE.moduloDbModuloEdit(moduloRepository.save(entity));
    }

    @Override
    public ModuloEdit read(Long id) {
        ModuloDb entity = moduloRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("MODULO_NOT_FOUND", "No se encontró el modulo con id: " + id));
        return ModuloMapper.INSTANCE.moduloDbModuloEdit(entity);
    }

    @Override
    public ModuloEdit update(Long id, ModuloEdit moduloEdit) {
        if (!id.equals(moduloEdit.getId())) {
            throw new EntityIllegalArgumentException("MODULO_ID_MISMATCH", "El ID proporcionado no coincide con el ID del modulo a actualizar.");
        }

        ModuloDb existingEntity = moduloRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("CUESTIONARIO_NOT_FOUND_FOR_UPDATE", "No se puede actualizar. El cuestionario con ID " + id + " no existe."));
        
        ModuloMapper.INSTANCE.updateModuloDbFromModuloEdit(moduloEdit, existingEntity);
        return ModuloMapper.INSTANCE.moduloDbModuloEdit(moduloRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (moduloRepository.existsById(id)) {
            moduloRepository.deleteById(id);
        }
    }

    // @Override
    // public List<ModuloEdit> getAllModulos() {
    //     return moduloRepository.findAll().stream()
    //         .map(ModuloMapper.INSTANCE::moduloDbModuloEdit)
    //         .collect(Collectors.toList());
    // }

    @Override
    public List<ModuloEdit> getModulosBySector(Long sectorId) {
        return moduloRepository.findBySectorId(sectorId).stream()
            .map(ModuloMapper.INSTANCE::moduloDbModuloEdit)
            .collect(Collectors.toList());
    }

    @Override
    public PaginaResponse<ModuloEdit> findAll(String[] filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<ModuloEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<ModuloDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<ModuloDb> page = moduloRepository.findAll(filtrosBusquedaSpecification, pageable);
            return ModuloMapper.pageToPaginaResponseModuloEdit(
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