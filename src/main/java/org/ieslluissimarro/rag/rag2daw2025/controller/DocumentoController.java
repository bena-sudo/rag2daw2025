package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.BindingResultErrorsResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoEdit;
import org.ieslluissimarro.rag.rag2daw2025.srv.DocumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("api/v1/documentos")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentoController {

    private final DocumentoService documentoService;

    @Operation(summary = "Crea un nuevo documento.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Documento creado exitosamente", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoEdit.class))),
        @ApiResponse(responseCode = "400", description = "Errores de validación en los datos proporcionados", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = BindingResultErrorsResponse.class)))
    })
    @PreAuthorize("@authorizationService.hasPermission('CREAR_DOCUMENTO_USUARIO')")
    @PostMapping
    public ResponseEntity<DocumentoEdit> create(@Valid @RequestBody DocumentoEdit documentoEdit, BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "DOCUMENTO_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(documentoService.create(documentoEdit));
    }

    @Operation(summary = "Obtiene un documento por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Documento encontrado", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoEdit.class))),
        @ApiResponse(responseCode = "404", description = "Documento no encontrado", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DocumentoEdit> read(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.read(id));
    }

    @Operation(summary = "Actualiza un documento existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Documento actualizado con éxito", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoEdit.class))),
        @ApiResponse(responseCode = "400", description = "Errores de validación en los datos proporcionados", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = BindingResultErrorsResponse.class))),
        @ApiResponse(responseCode = "404", description = "Documento no encontrado", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<DocumentoEdit> update(@PathVariable Long id, @Valid @RequestBody DocumentoEdit documentoEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "DOCUMENTO_UPDATE_VALIDATION");
        return ResponseEntity.ok(documentoService.update(id, documentoEdit));
    }
    
    @Operation(summary = "Elimina un documento por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Documento eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Documento no encontrado", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @PreAuthorize("@authorizationService.hasPermission('ELIMINAR_DOCUMENTO_USUARIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene todos los documentos.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de documentos obtenida correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de filtrado u ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER'", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("@authorizationService.hasPermission('VER_DOCUMENTOS_USUARIO')")
    @GetMapping
    public ResponseEntity<PaginaResponse<DocumentoEdit>> getAllDocumentos(
                        @RequestParam(required = false) List<String> filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size,
                        @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(documentoService.findAll(filter, page, size, sort));
    }
}