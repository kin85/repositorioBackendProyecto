package org.simarro.proyecto.chatbot.controller;

import java.util.List;

import org.simarro.proyecto.chatbot.filters.FiltroException;
import org.simarro.proyecto.chatbot.filters.model.PaginaResponse;
import org.simarro.proyecto.chatbot.helper.BindingResultHelper;
import org.simarro.proyecto.chatbot.model.dto.MensajeEdit;
import org.simarro.proyecto.chatbot.service.MensajeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/mensajes")
@CrossOrigin(origins = "http://localhost:4200")
public class MensajeController {

    private final MensajeService mensajeService;

    @PostMapping
    public ResponseEntity<MensajeEdit> create(@Valid @RequestBody MensajeEdit mensajeEdit, BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "MENSAJE_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(mensajeService.create(mensajeEdit));
    }


    @GetMapping("/{id}")
    public ResponseEntity<MensajeEdit> read(@PathVariable Long id) {
        return ResponseEntity.ok(mensajeService.read(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<MensajeEdit> update(@PathVariable Long id, @Valid @RequestBody MensajeEdit mensajeEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "MENSAJE_UPDATE_VALIDATION");
        return ResponseEntity.ok(mensajeService.update(id, mensajeEdit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mensajeService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<PaginaResponse<MensajeEdit>> getAllMensajes(
                        @RequestParam(required = false) String[] filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size,
                        @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(mensajeService.findAll(filter, page, size, sort));
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MensajeEdit>> getMensajesByUsuarioId(@PathVariable Long usuarioId) {
        List<MensajeEdit> mensajes = mensajeService.getMensajesByUsuarioId(usuarioId);
        return mensajes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(mensajes);
    }

    @GetMapping("/acreditacion/{acreditacionId}")
    public ResponseEntity<List<MensajeEdit>> getMensajesByAcreditacionId(@PathVariable Long acreditacionId) {
        List<MensajeEdit> mensajes = mensajeService.getMensajesByAcreditacionId(acreditacionId);
        return mensajes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(mensajes);
    }
}
