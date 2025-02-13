package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.IdEntityLong;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkList;
import org.ieslluissimarro.rag.rag2daw2025.srv.DocumentoChunkService;
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
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentChunkController {
    private final DocumentoChunkService documentChunkService;

    @PostMapping("/chunk")
    public ResponseEntity<DocumentoChunkEdit> create(@Valid @RequestBody DocumentoChunkEdit documentoChunkEdit, BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "CHUNK_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(documentChunkService.create(documentoChunkEdit));
    }
    @GetMapping("/chunk/{id}")
    public ResponseEntity<DocumentoChunkInfo> read(@PathVariable String id) {
            return ResponseEntity.ok(documentChunkService.read(new IdEntityLong(id).getValue()));
    }

    @PutMapping("/chunk/{id}")
    public ResponseEntity<DocumentoChunkEdit> update(@PathVariable String id,
                    @Valid @RequestBody DocumentoChunkEdit documentoChunkEdit, BindingResult bindingResult) {
            BindingResultHelper.validateBindingResult(bindingResult, "CHUNK_UPDATE_VALIDATION");
            return ResponseEntity.ok(documentChunkService.update(new IdEntityLong(id).getValue(), documentoChunkEdit));
    }

    @DeleteMapping("/chunk/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        documentChunkService.delete(new IdEntityLong(id).getValue());
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/chunks")
    public ResponseEntity<PaginaResponse<DocumentoChunkList>> getAllChunksGET(
                    @RequestParam(required = false) List<String> filter,
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "3") int size,
                    @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
            return ResponseEntity.ok(documentChunkService.findAll(filter, page, size, sort));
    }

    @PostMapping("/chunks")
    public ResponseEntity<PaginaResponse<DocumentoChunkList>> getAllChunksPOST(
                    @Valid @RequestBody PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
            return ResponseEntity.ok(documentChunkService.findAll(peticionListadoFiltrado));
    }
}
