package org.simarro.proyecto.chatbot.controller;

import java.util.List;

import org.simarro.proyecto.chatbot.exception.CustomErrorResponse;
import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.db.UsuarioDb;
import org.simarro.proyecto.chatbot.model.dto.UsuarioInfo;
import org.simarro.proyecto.chatbot.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public UsuarioInfo createUsuario(@RequestBody UsuarioDb usuario) {
        return usuarioService.createUsuario(usuario);
    }

    @GetMapping("/id/{id}")
    public UsuarioInfo getUsuarioById(@PathVariable long id) {
        return usuarioService.getUsuarioById(id);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<UsuarioDb> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDb usuario) {
        return ResponseEntity.ok(usuarioService.updateUsuario(id, usuario));
    }

    @DeleteMapping("/id/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }

    @Operation(summary = "Obtiene todos los usuarios.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de filtrado u ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER'", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<PaginaResponse<UsuarioInfo>> getAllUsuarios(
                        @RequestParam(required = false) String[] filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size,
                        @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(usuarioService.findAll(filter, page, size, sort));
    }
}
