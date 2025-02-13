package org.simarro.proyecto.chatbot.service.impl;

import java.util.List;

import org.simarro.proyecto.chatbot.exception.EntityIllegalArgumentException;
import org.simarro.proyecto.chatbot.exception.EntityNotFoundException;
import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.filters.utils.PaginationFactory;
import org.simarro.proyecto.chatbot.helper.PeticionListadoFiltradoConverter;
import org.simarro.proyecto.chatbot.model.db.UsuarioUnidadesCompetenciaDb;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.UsuarioUnidadesCompetenciaEdit;
import org.simarro.proyecto.chatbot.repository.UsuarioUnidadesCompetenciaRepository;
import org.simarro.proyecto.chatbot.service.UsuarioUnidadesCompetenciaService;
import org.simarro.proyecto.chatbot.service.mapper.UsuarioUnidadesCompetenciaMapper;
import org.simarro.proyecto.chatbot.service.specification.FiltroBusquedaSpecification;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioUnidadesCompetenciaImpl implements UsuarioUnidadesCompetenciaService {

    private final UsuarioUnidadesCompetenciaRepository usuarioUnidadesCompetenciaRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public UsuarioUnidadesCompetenciaEdit create(UsuarioUnidadesCompetenciaEdit usuarioUnidadesCompetenciaEdit) {
        if (usuarioUnidadesCompetenciaEdit.getId() != null) {
            throw new EntityIllegalArgumentException("USUARIO_UNIDAD_COMPETENCIA_ID_MISMATCH",
                    "El ID debe ser nulo al crear una nueva relacion entre usuario y unidad de competencia");
        }

        UsuarioUnidadesCompetenciaDb entity = UsuarioUnidadesCompetenciaMapper.INSTANCE
                .usuarioUnidadesCompetenciaDbUsuarioUnidadesCompetenciaEdit(usuarioUnidadesCompetenciaEdit);
        return UsuarioUnidadesCompetenciaMapper.INSTANCE.usuarioUnidadesCompetenciaEditUsuarioUnidadesCompetenciaDb(
                usuarioUnidadesCompetenciaRepository.save(entity));
    }

    @Override
    public UsuarioUnidadesCompetenciaEdit read(Long id) {
        UsuarioUnidadesCompetenciaDb entity = usuarioUnidadesCompetenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UC_NOT_FOUND",
                        "No se encontró la unidad de competencia con id: " + id));
        return UsuarioUnidadesCompetenciaMapper.INSTANCE.usuarioUnidadesCompetenciaEditUsuarioUnidadesCompetenciaDb(entity);
    }

    @Override
    public UsuarioUnidadesCompetenciaEdit update(Long id,
            UsuarioUnidadesCompetenciaEdit usuarioUnidadesCompetenciaEdit) {
        if (!id.equals(usuarioUnidadesCompetenciaEdit.getId())) {
            throw new EntityIllegalArgumentException("CUESTIONARIO_ID_MISMATCH",
                    "El ID proporcionado no coincide con el ID del cuestionario a actualizar.");
        }

        UsuarioUnidadesCompetenciaDb existingEntity = usuarioUnidadesCompetenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CUESTIONARIO_NOT_FOUND_FOR_UPDATE",
                        "No se puede actualizar. El cuestionario con ID " + id + " no existe."));

        UsuarioUnidadesCompetenciaMapper.INSTANCE.updateUsuarioUnidadesCompetenciaDbFromUsuarioUnidadesCompetencia(usuarioUnidadesCompetenciaEdit,
                existingEntity);
        return UsuarioUnidadesCompetenciaMapper.INSTANCE
                .usuarioUnidadesCompetenciaEditUsuarioUnidadesCompetenciaDb(usuarioUnidadesCompetenciaRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (usuarioUnidadesCompetenciaRepository.existsById(id)) {
            usuarioUnidadesCompetenciaRepository.deleteById(id);
        }
    }

    // @Override
    // public List<UsuarioUnidadesCompetenciaEdit> getAllUsuarioUnidadCompetencias() {
    //     return UsuarioUnidadesCompetenciaMapper.INSTANCE.usuarioUnidadesCompetenciaDbListToUsuarioUnidadesCompetenciaEditList(usuarioUnidadesCompetenciaRepository.findAll());
    // }

    @Override
    public PaginaResponse<UsuarioUnidadesCompetenciaEdit> findAll(String[] filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<UsuarioUnidadesCompetenciaEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<UsuarioUnidadesCompetenciaDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<UsuarioUnidadesCompetenciaDb> page = usuarioUnidadesCompetenciaRepository.findAll(filtrosBusquedaSpecification, pageable);
            return UsuarioUnidadesCompetenciaMapper.pageToPaginaResponseUsuarioUnidadesCompetenciaEdit(
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