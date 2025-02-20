package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/etiqueta")
public class EtiquetaController {

    private final EtiquetaService etiquetaService;

    /**
     * Crea una nueva etiqueta en el sistema.
     *
     * @param etiquetaEdit  Objeto que contiene los datos de la etiqueta a crear.
     *                      Debe cumplir con las validaciones definidas.
     * @param bindingResult Resultado de las validaciones de los datos
     *                      proporcionados.
     * @return Un objeto EtiquetaEdit con los datos de la etiqueta creada, junto con
     *         el código HTTP 201 (CREATED).
     */
    @Operation(summary = "Crea una nueva etiqueta en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Etiqueta creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EtiquetaEdit.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos proporcionados", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<EtiquetaEdit> create(@Valid @RequestBody EtiquetaEdit etiquetaEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "ETIQUETA_CREATE_VALIDATION");
        return ResponseEntity.status(HttpStatus.CREATED).body(etiquetaService.create(etiquetaEdit));
    }

    /**
     * Obtiene los datos de una etiqueta a partir de su identificador.
     *
     * @param id Identificador de la etiqueta a buscar.
     * @return Un objeto EtiquetaEdit con los datos de la etiqueta encontrada, junto
     *         con el código HTTP 200 (OK).
     */
    @Operation(summary = "Obtiene los datos de una etiqueta dado su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Etiqueta encontrada con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EtiquetaEdit.class))),
            @ApiResponse(responseCode = "404", description = "Etiqueta no encontrada", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EtiquetaEdit> read(@PathVariable String id) {
        return ResponseEntity.ok(etiquetaService.read(new IdEntityLong(id).getValue()));
    }

    /**
     * Actualiza los datos de una etiqueta existente.
     *
     * @param id            Identificador de la etiqueta a actualizar.
     * @param etiquetaEdit  Objeto que contiene los datos actualizados de la
     *                      etiqueta.
     * @param bindingResult Resultado de las validaciones de los datos
     *                      proporcionados.
     * @return Un objeto EtiquetaEdit con los datos actualizados, junto con el
     *         código HTTP 200 (OK).
     */
    @Operation(summary = "Actualiza los datos de una etiqueta existente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Etiqueta actualizada con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EtiquetaEdit.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos proporcionados", content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EtiquetaEdit> update(@PathVariable String id, @Valid @RequestBody EtiquetaEdit etiquetaEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "ETIQUETA_UPDATE_VALIDATION");
        return ResponseEntity.ok(etiquetaService.update(new IdEntityLong(id).getValue(), etiquetaEdit));
    }

    /**
     * Elimina una etiqueta del sistema.
     *
     * @param id Identificador de la etiqueta a eliminar.
     * @return Un código HTTP 204 (NO CONTENT) indicando que la eliminación fue
     *         exitosa.
     */
    @Operation(summary = "Elimina una etiqueta del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Etiqueta eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en el formato del identificador", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        etiquetaService.delete(new IdEntityLong(id).getValue());
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una lista paginada de etiquetas aplicando filtros a partir de una
     * petición.
     *
     * @param peticionListadoFiltrado Objeto que contiene los criterios de filtrado
     *                                y paginación.
     * @return Una respuesta paginada con la lista de etiquetas que cumplen los
     *         criterios, junto con el código HTTP 200 (OK).
     * @throws FiltroException En caso de que los filtros aplicados no sean válidos.
     */
    @Operation(summary = "Obtiene una lista paginada de etiquetas aplicando filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de etiquetas obtenida con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error en los filtros aplicados", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/etiquetas")
    public ResponseEntity<PaginaResponse<EtiquetaList>> getAll(
            @Valid @RequestBody PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        return ResponseEntity.ok(etiquetaService.findAll(peticionListadoFiltrado));
    }

    /**
     * Obtiene una lista paginada de etiquetas aplicando filtros a través de
     * parámetros en la URL.
     *
     * @param filter Lista de filtros opcionales.
     * @param page   Número de página a obtener.
     * @param size   Tamaño de la página.
     * @param sort   Criterios de ordenación.
     * @return Una respuesta paginada con la lista de etiquetas que cumplen los
     *         criterios, junto con el código HTTP 200 (OK).
     * @throws FiltroException En caso de que los filtros aplicados no sean válidos.
     */
    @Operation(summary = "Obtiene una lista paginada de etiquetas aplicando filtros vía parámetros de URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de etiquetas obtenida con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error en los filtros o parámetros proporcionados", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/etiquetas")
    public ResponseEntity<PaginaResponse<EtiquetaList>> getAll(
            @RequestParam(required = false) List<String> filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(etiquetaService.findAll(filter, page, size, sort));
    }
}
