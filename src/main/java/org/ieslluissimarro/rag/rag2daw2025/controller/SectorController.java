package org.ieslluissimarro.rag.rag2daw2025.controller;


import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.SectorEdit;
import org.ieslluissimarro.rag.rag2daw2025.srv.SectorService;
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
@RequestMapping("api/v1/sectores")
@CrossOrigin(origins = "http://localhost:4200")
public class SectorController {

    private final SectorService sectorService;

    @PostMapping
    public ResponseEntity<SectorEdit> create(@Valid @RequestBody SectorEdit sectorEdit, BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "SECTOR_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(sectorService.create(sectorEdit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectorEdit> read(@PathVariable Long id) {
        return ResponseEntity.ok(sectorService.read(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectorEdit> update(@PathVariable Long id, @Valid @RequestBody SectorEdit sectorEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "SECTOR_UPDATE_VALIDATION");
        return ResponseEntity.ok(sectorService.update(id, sectorEdit));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sectorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping
    // public List<SectorEdit> getAllSectores() {
    //     return sectorService.getAllSectores();
    // }

    @Operation(summary = "Obtiene todos los sectores.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de sectores obtenida correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de filtrado u ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER'", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<PaginaResponse<SectorEdit>> getAllSectores(
                        @RequestParam(required = false) List<String> filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "1000") int size,
                        @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(sectorService.findAll(filter, page, size, sort));
    }
}
