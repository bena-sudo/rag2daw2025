package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.IdEntityLong;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaList;
import org.ieslluissimarro.rag.rag2daw2025.srv.EtiquetaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/api/v1/etiqueta")
public class EtiquetaController {
    private final EtiquetaService etiquetaService;

    @PostMapping
    public ResponseEntity<EtiquetaEdit> create(@Valid @RequestBody EtiquetaEdit etiquetaEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "ETIQUETA_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(etiquetaService.create(etiquetaEdit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtiquetaEdit> read(@PathVariable String id) {
        return ResponseEntity.ok(etiquetaService.read(new IdEntityLong(id).getValue()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtiquetaEdit> update(@PathVariable String id, @Valid @RequestBody EtiquetaEdit etiquetaEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "ETIQUETA_UPDATE_VALIDATION");
        return ResponseEntity.ok(etiquetaService.update(new IdEntityLong(id).getValue(), etiquetaEdit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        etiquetaService.delete(new IdEntityLong(id).getValue());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/etiquetas")
    public ResponseEntity<PaginaResponse<EtiquetaList>> getAll(
            @Valid @RequestBody PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        return ResponseEntity.ok(etiquetaService.findAll(peticionListadoFiltrado));
    }

    @GetMapping("/etiquetas")
    public ResponseEntity<PaginaResponse<EtiquetaList>> getAll(
            @RequestParam(required = false) String[] filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String[] sort) throws FiltroException {
        return ResponseEntity.ok(etiquetaService.findAll(filter, page, size, sort));
    }
}