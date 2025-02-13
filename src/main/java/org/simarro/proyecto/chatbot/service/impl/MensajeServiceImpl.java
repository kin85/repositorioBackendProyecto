package org.simarro.proyecto.chatbot.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.simarro.proyecto.chatbot.exception.EntityIllegalArgumentException;
import org.simarro.proyecto.chatbot.exception.EntityNotFoundException;
import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.filters.utils.PaginationFactory;
import org.simarro.proyecto.chatbot.helper.PeticionListadoFiltradoConverter;
import org.simarro.proyecto.chatbot.model.db.MensajeDb;
import org.simarro.proyecto.chatbot.model.dto.MensajeEdit;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.repository.MensajeRepository;
import org.simarro.proyecto.chatbot.service.MensajeService;
import org.simarro.proyecto.chatbot.service.mapper.MensajeMapper;
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
public class MensajeServiceImpl implements MensajeService {

    private final MensajeRepository mensajeRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public MensajeEdit create(MensajeEdit mensajeEdit) {
        if (mensajeEdit.getId() != null) {
            throw new EntityIllegalArgumentException("MENSAJE_ID_MISMATCH", "El ID debe ser nulo al crear un nuevo mensaje");
        }
        MensajeDb entity = MensajeMapper.INSTANCE.mensajeEditToMensajeDb(mensajeEdit);
        return MensajeMapper.INSTANCE.mensajeDbToMensajeEdit(mensajeRepository.save(entity));
    }

    @Override
    public MensajeEdit read(Long id) {
        MensajeDb entity = mensajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MENSAJE_NOT_FOUND", "No se encontró el mensaje con id: " + id));
        return MensajeMapper.INSTANCE.mensajeDbToMensajeEdit(entity);
    }

    @Override
    public MensajeEdit update(Long id, MensajeEdit mensajeEdit) {
        if (!id.equals(mensajeEdit.getId())) {
            throw new EntityIllegalArgumentException("MENSAJE_ID_MISMATCH", "El ID proporcionado no coincide con el ID del mensaje a actualizar.");
        }
        MensajeDb existingEntity = mensajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MENSAJE_NOT_FOUND_FOR_UPDATE", "No se puede actualizar. El mensaje con ID " + id + " no existe."));
        MensajeMapper.INSTANCE.updateMensajeDbFromMensajeEdit(mensajeEdit, existingEntity);
        return MensajeMapper.INSTANCE.mensajeDbToMensajeEdit(mensajeRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (mensajeRepository.existsById(id)) {
            mensajeRepository.deleteById(id);
        }
    }

    @Override
    public PaginaResponse<MensajeEdit> findAll(String[] filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<MensajeEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<MensajeDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<MensajeDb> page = mensajeRepository.findAll(filtrosBusquedaSpecification, pageable);
            return MensajeMapper.pageToPaginaResponseMensajeEdit(
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

    @Override
    public List<MensajeEdit> getMensajesByUsuarioId(Long usuarioId) {
        return mensajeRepository.findByUsuarioId(usuarioId).stream()
            .map(MensajeMapper.INSTANCE::mensajeDbToMensajeEdit)
            .collect(Collectors.toList());
    }

    @Override
    public List<MensajeEdit> getMensajesByAcreditacionId(Long acreditacionId) {
        return mensajeRepository.findByAcreditacionId(acreditacionId).stream()
            .map(MensajeMapper.INSTANCE::mensajeDbToMensajeEdit)
            .collect(Collectors.toList());
    }
}