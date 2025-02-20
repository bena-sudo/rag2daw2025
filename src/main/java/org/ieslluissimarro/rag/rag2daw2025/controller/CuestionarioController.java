package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.BindingResultErrorsResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.CuestionarioEdit;
import org.ieslluissimarro.rag.rag2daw2025.srv.CuestionarioService;
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
@RequestMapping("api/v1/cuestionarios")
public class CuestionarioController {

    private final CuestionarioService cuestionarioService;

    @Operation(summary = "Crea un nuevo cuestionario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cuestionario creado exitosamente", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CuestionarioEdit.class))),
        @ApiResponse(responseCode = "400", description = "Errores de validación en los datos proporcionados", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = BindingResultErrorsResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CuestionarioEdit> create(@Valid @RequestBody CuestionarioEdit cuestionarioEdit, BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "CUESTIONARIO_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(cuestionarioService.create(cuestionarioEdit));
    }

    @Operation(summary = "Obtiene un cuestionario por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuestionario encontrado", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CuestionarioEdit.class))),
        @ApiResponse(responseCode = "404", description = "Cuestionario no encontrado", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CuestionarioEdit> read(@PathVariable Long id) {
        return ResponseEntity.ok(cuestionarioService.read(id));
    }

    @Operation(summary = "Actualiza un cuestionario existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuestionario actualizado con éxito", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CuestionarioEdit.class))),
        @ApiResponse(responseCode = "400", description = "Errores de validación en los datos proporcionados", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = BindingResultErrorsResponse.class))),
        @ApiResponse(responseCode = "404", description = "Cuestionario no encontrado", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CuestionarioEdit> update(@PathVariable Long id, @Valid @RequestBody CuestionarioEdit unidadCompetenciaEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "CUESTIONARIO_UPDATE_VALIDATION");
        return ResponseEntity.ok(cuestionarioService.update(id, unidadCompetenciaEdit));
    }
    
    @Operation(summary = "Elimina un cuestionario por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cuestionario eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Cuestionario no encontrado", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cuestionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene todos los cuestionarios.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de cuestionarios obtenida correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de filtrado u ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER'", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<PaginaResponse<CuestionarioEdit>> getAllCuestionarios(
                        @RequestParam(required = false) List<String> filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "1000") int size,
                        @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(cuestionarioService.findAll(filter, page, size, sort));
    }
}