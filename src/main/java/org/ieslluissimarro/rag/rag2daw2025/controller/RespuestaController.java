package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.db.RespuestaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.RespuestaEdit;
import org.ieslluissimarro.rag.rag2daw2025.srv.RespuestaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("api/v1/respuestas")
public class RespuestaController {

    private final RespuestaService respuestaService;

    @PostMapping
    public ResponseEntity<RespuestaEdit> create(@Valid @RequestBody RespuestaEdit respuestaEdit, BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "RESPUESTA_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaService.create(respuestaEdit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaEdit> read(@PathVariable Long id) {
        return ResponseEntity.ok(respuestaService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        respuestaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping
    // public ResponseEntity<List<RespuestaEdit>> getAllRespuestas() {
    //     List<RespuestaEdit> respuestas = respuestaService.getAllRespuestas();
    //     return respuestas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(respuestas);
    // }

    @Operation(summary = "Obtiene todas las respuestas.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de respuestas obtenida correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de filtrado u ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER'", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<PaginaResponse<RespuestaEdit>> getAllRespuestas(
                        @RequestParam(required = false) List<String> filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size,
                        @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(respuestaService.findAll(filter, page, size, sort));
    }

    @PostMapping("/cuestionario")
    public ResponseEntity<List<RespuestaDb>> crearRespuestas(@RequestBody List<RespuestaEdit> respuestas) {
        try {
            List<RespuestaDb> respuestasGuardadas = respuestaService.createRespuestas(respuestas);
            return ResponseEntity.ok(respuestasGuardadas);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}