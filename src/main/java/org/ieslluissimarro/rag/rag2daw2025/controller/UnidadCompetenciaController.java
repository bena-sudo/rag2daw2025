package org.ieslluissimarro.rag.rag2daw2025.controller;


import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UnidadCompetenciaEdit;
import org.ieslluissimarro.rag.rag2daw2025.srv.UnidadCompetenciaService;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/unidadCompetencia")
public class UnidadCompetenciaController {

    private final UnidadCompetenciaService unidadCompetenciaService;

    @PostMapping
    public ResponseEntity<UnidadCompetenciaEdit> create(@Valid @RequestBody UnidadCompetenciaEdit unidadCompetenciaEdit, BindingResult bindingResult) {
        // Comprueba errores de validación y si los hay lanza una BindingResultException
        // con el errorCode
        BindingResultHelper.validateBindingResult(bindingResult, "UC_CREATE_VALIDATION");
        // No hay error de validación y procedemos a crear el nuevo registro
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadCompetenciaService.create(unidadCompetenciaEdit));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UnidadCompetenciaEdit> read(@PathVariable String id) {
        return ResponseEntity.ok(unidadCompetenciaService.read(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadCompetenciaEdit> update(@PathVariable String id, @Valid @RequestBody UnidadCompetenciaEdit unidadCompetenciaEdit,
            BindingResult bindingResult) {
        // Comprueba errores de validación y si los hay lanza una BindingResultException
        // con el errorCode
        BindingResultHelper.validateBindingResult(bindingResult, "STUDENT_UPDATE_VALIDATION");
        // No hay error de validación y procedemos a modificar el registro
        return ResponseEntity.ok(unidadCompetenciaService.update(id, unidadCompetenciaEdit));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        unidadCompetenciaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene todas las Unidades de competencia.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de Unidades de competencia obtenida correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de filtrado u ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER'", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<PaginaResponse<UnidadCompetenciaEdit>> getAllUnidadCompetencias(
                        @RequestParam(required = false) List<String> filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "1000") int size,
                        @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(unidadCompetenciaService.findAll(filter, page, size, sort));
    }

    @GetMapping("/modulo/{moduloId}")
    public ResponseEntity<List<UnidadCompetenciaEdit>> getUnidadesCompetenciaByModulo(@PathVariable Long moduloId) {
        List<UnidadCompetenciaEdit> unidadesCompetencia = unidadCompetenciaService.getUnidadesCompetenciaByModulo(moduloId);
        return unidadesCompetencia.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(unidadesCompetencia);
    }
}
