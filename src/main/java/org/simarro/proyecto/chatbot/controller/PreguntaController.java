package org.simarro.proyecto.chatbot.controller;


import java.util.List;

import org.simarro.proyecto.chatbot.helper.BindingResultHelper;
import org.simarro.proyecto.chatbot.model.dto.PreguntaEdit;
import org.simarro.proyecto.chatbot.service.PreguntaService;
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
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/preguntas")
@CrossOrigin(origins = "http://localhost:4200")
public class PreguntaController {

    private final PreguntaService preguntaService;

    @PostMapping
    public ResponseEntity<PreguntaEdit> create(@Valid @RequestBody PreguntaEdit preguntaEdit, BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "PREGUNTA_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(preguntaService.create(preguntaEdit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreguntaEdit> read(@PathVariable Long id) {
        return ResponseEntity.ok(preguntaService.read(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<PreguntaEdit> update(@PathVariable Long id, @Valid @RequestBody PreguntaEdit preguntaEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "PREGUNTA_UPDATE_VALIDATION");
        return ResponseEntity.ok(preguntaService.update(id, preguntaEdit));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        preguntaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<PreguntaEdit> getAllPreguntas() {
        return preguntaService.getAllPreguntas();
    }

    @GetMapping("/cuestionario/{cuestionarioId}")
    public ResponseEntity<List<PreguntaEdit>> obtenerPreguntasPorCuestionario(@PathVariable Long cuestionarioId) {
        List<PreguntaEdit> preguntas = preguntaService.obtenerPreguntasPorCuestionario(cuestionarioId);
        return preguntas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(preguntas);
    }
}
