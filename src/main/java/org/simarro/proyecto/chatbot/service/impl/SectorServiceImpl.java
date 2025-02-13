package org.simarro.proyecto.chatbot.service.impl;


import java.util.List;

import org.simarro.proyecto.chatbot.exception.EntityIllegalArgumentException;
import org.simarro.proyecto.chatbot.exception.EntityNotFoundException;
import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.filters.utils.PaginationFactory;
import org.simarro.proyecto.chatbot.helper.PeticionListadoFiltradoConverter;
import org.simarro.proyecto.chatbot.model.db.SectorDb;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.SectorEdit;
import org.simarro.proyecto.chatbot.repository.SectorRepository;
import org.simarro.proyecto.chatbot.service.SectorService;
import org.simarro.proyecto.chatbot.service.mapper.SectorMapper;
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
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public SectorEdit create(SectorEdit sectorEdit) {
        if (sectorEdit.getId() !=  null) {
            throw new EntityIllegalArgumentException("SECTOR_ID_MISMATCH", "El ID debe ser nulo al crear un nuevo sector");
        }

        SectorDb entity = SectorMapper.INSTANCE.sectorEditSectorDb(sectorEdit);
        return SectorMapper.INSTANCE.sectorDbSectorEdit(sectorRepository.save(entity));
    }

    @Override
    public SectorEdit read(Long id) {
        SectorDb entity = sectorRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("SECTOR_NOT_FOUND", "No se encontró el sector con id: " + id));
        return SectorMapper.INSTANCE.sectorDbSectorEdit(entity);
    }

    @Override
    public SectorEdit update(Long id, SectorEdit sectorEdit) {
        if (!id.equals(sectorEdit.getId())) {
            throw new EntityIllegalArgumentException("CUESTIONARIO_ID_MISMATCH", "El ID proporcionado no coincide con el ID del cuestionario a actualizar.");
        }

        SectorDb existingEntity = sectorRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("SECTOR_NOT_FOUND_FOR_UPDATE", "No se puede actualizar. El sector con ID " + id + " no existe."));
        
        SectorMapper.INSTANCE.updateSectorDbFromSectorEdit(sectorEdit, existingEntity);
        return SectorMapper.INSTANCE.sectorDbSectorEdit(sectorRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (sectorRepository.existsById(id)) {
            sectorRepository.deleteById(id);
        }
    }

    // @Override
    // public List<SectorEdit> getAllSectores() {
    //     return sectorRepository.findAll().stream()
    //         .map(SectorMapper.INSTANCE::sectorDbSectorEdit)
    //         .collect(Collectors.toList());
    // }

    @Override
    public PaginaResponse<SectorEdit> findAll(String[] filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<SectorEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<SectorDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<SectorDb> page = sectorRepository.findAll(filtrosBusquedaSpecification, pageable);
            return SectorMapper.pageToPaginaResponseSectorEdit(
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
