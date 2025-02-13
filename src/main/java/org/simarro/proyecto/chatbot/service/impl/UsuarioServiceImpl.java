package org.simarro.proyecto.chatbot.service.impl;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.filters.utils.PaginationFactory;
import org.simarro.proyecto.chatbot.helper.PeticionListadoFiltradoConverter;
import org.simarro.proyecto.chatbot.model.db.UsuarioDb;
import org.simarro.proyecto.chatbot.model.dto.PeticionListadoFiltrado;
import org.simarro.proyecto.chatbot.model.dto.UsuarioInfo;
import org.simarro.proyecto.chatbot.repository.UsuarioRepository;
import org.simarro.proyecto.chatbot.service.UsuarioService;
import org.simarro.proyecto.chatbot.service.mapper.UsuarioMapper;
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
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public UsuarioInfo createUsuario(UsuarioDb usuario) {
        UsuarioDb savedUsuario = usuarioRepository.save(usuario);
        return new UsuarioInfo(
            savedUsuario.getId(),
            savedUsuario.getNombre(),
            savedUsuario.getNickname(),
            savedUsuario.getEmail(),
            savedUsuario.getTelefono(),
            savedUsuario.getFechaNacimiento(),
            savedUsuario.getEstado()
        );
    }

    @Override
    public UsuarioInfo getUsuarioById(long id) {
        UsuarioDb usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return null;
        }
        return new UsuarioInfo(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getNickname(),
            usuario.getEmail(),
            usuario.getTelefono(),
            usuario.getFechaNacimiento(),
            usuario.getEstado()
        );
    }

    @Override
    public UsuarioDb updateUsuario(Long id, UsuarioDb usuario) {
        UsuarioDb existingUsuario = usuarioRepository.findById(id).orElse(null);
        if (existingUsuario != null) {
            existingUsuario.setNombre(usuario.getNombre());
            existingUsuario.setNickname(usuario.getNickname());
            existingUsuario.setEmail(usuario.getEmail());
            existingUsuario.setPassword(usuario.getPassword());
            existingUsuario.setPasswordSalt(usuario.getPasswordSalt());
            existingUsuario.setTelefono(usuario.getTelefono());
            existingUsuario.setFechaNacimiento(usuario.getFechaNacimiento());
            existingUsuario.setEstado(usuario.getEstado());
            return usuarioRepository.save(existingUsuario);
        }
        return null;
    }

    @Override
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public PaginaResponse<UsuarioInfo> findAll(String[] filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<UsuarioInfo> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<UsuarioDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<UsuarioDb> page = usuarioRepository.findAll(filtrosBusquedaSpecification, pageable);
            return UsuarioMapper.pageToPaginaResponseUsuarioInfo(
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
