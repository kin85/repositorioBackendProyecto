package org.simarro.proyecto.chatbot.controller;

import java.util.List;

import org.simarro.proyecto.chatbot.exception.CustomErrorResponse;
import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.model.db.SesionDb;
import org.simarro.proyecto.chatbot.model.dto.SesionEdit;
import org.simarro.proyecto.chatbot.service.SesionService;
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
@RequestMapping("api/v1/sesiones")
@CrossOrigin(origins = "http://localhost:4200")
public class SesionController {

    private final SesionService sesionService;

    @PostMapping
    public SesionDb createSesion(@RequestBody SesionDb sesion) {
        return sesionService.createSesion(sesion);
    }

    @GetMapping("/id/{id}")
    public SesionDb getSesionById(@PathVariable long id) {
        return sesionService.getSesionById(id);
    }

    // @GetMapping
    // public List<SesionDb> getAllSesiones() {
    //     return sesionService.getAllSesiones();
    // }

    @PutMapping("/id/{id}")
    public SesionDb updateSesion(@PathVariable long id, @RequestBody SesionDb sesion) {
        return sesionService.updateSesion(id, sesion);
    }

    @DeleteMapping("/id/{id}")
    public void deleteSesion(@PathVariable long id) {
        sesionService.deleteSesion(id);
    }

    @Operation(summary = "Obtiene todas las sesiones.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de sesiones obtenida correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de filtrado u ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER'", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<PaginaResponse<SesionEdit>> getAllSesiones(
                        @RequestParam(required = false) String[] filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size,
                        @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(sesionService.findAll(filter, page, size, sort));
    }
}
