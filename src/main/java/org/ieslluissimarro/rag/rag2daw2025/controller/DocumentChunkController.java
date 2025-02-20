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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin()
public class DocumentChunkController {

        private final DocumentoChunkService documentChunkService;

        /**
         * Crea un nuevo chunk de documento en el sistema.
         *
         * @param documentoChunkEdit Objeto que contiene los datos del chunk a crear.
         *                           Debe cumplir con las validaciones definidas.
         * @param bindingResult      Resultado de las validaciones de los datos
         *                           proporcionados.
         * @return Un objeto DocumentoChunkEdit con los datos del chunk creado, junto
         *         con el código HTTP 201 (CREATED).
         */
        @Operation(summary = "Crea un nuevo chunk de documento")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Chunk creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoChunkEdit.class))),
                        @ApiResponse(responseCode = "400", description = "Error de validación en los datos proporcionados", content = @Content(mediaType = "application/json"))
        })
        @PostMapping("/chunk")
        public ResponseEntity<DocumentoChunkEdit> create(@Valid @RequestBody DocumentoChunkEdit documentoChunkEdit,
                        BindingResult bindingResult) {
                BindingResultHelper.validateBindingResult(bindingResult, "CHUNK_CREATE_VALIDATION");
                return ResponseEntity.status(HttpStatus.CREATED).body(documentChunkService.create(documentoChunkEdit));
        }

        /**
         * Obtiene la información de un chunk de documento a partir de su identificador.
         *
         * @param id Identificador del chunk a buscar.
         * @return Un objeto DocumentoChunkInfo con los datos del chunk y un código HTTP
         *         200 (OK).
         */
        @Operation(summary = "Obtiene la información de un chunk de documento por su ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Chunk encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoChunkInfo.class))),
                        @ApiResponse(responseCode = "404", description = "Chunk no encontrado", content = @Content(mediaType = "application/json"))
        })
        @GetMapping("/chunk/{id}")
        public ResponseEntity<DocumentoChunkInfo> read(@PathVariable String id) {
                return ResponseEntity.ok(documentChunkService.read(new IdEntityLong(id).getValue()));
        }

        /**
         * Actualiza los datos de un chunk de documento existente.
         *
         * @param id                 Identificador del chunk a actualizar.
         * @param documentoChunkEdit Objeto que contiene los datos actualizados del
         *                           chunk.
         * @param bindingResult      Resultado de las validaciones de los datos
         *                           proporcionados.
         * @return Un objeto DocumentoChunkEdit con los datos actualizados y un código
         *         HTTP 200 (OK).
         */
        @Operation(summary = "Actualiza los datos de un chunk de documento existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Chunk actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoChunkEdit.class))),
                        @ApiResponse(responseCode = "400", description = "Error de validación en los datos proporcionados", content = @Content(mediaType = "application/json"))
        })
        @PutMapping("/chunk/{id}")
        public ResponseEntity<DocumentoChunkEdit> update(@PathVariable String id,
                        @Valid @RequestBody DocumentoChunkEdit documentoChunkEdit, BindingResult bindingResult) {
                BindingResultHelper.validateBindingResult(bindingResult, "CHUNK_UPDATE_VALIDATION");
                return ResponseEntity
                                .ok(documentChunkService.update(new IdEntityLong(id).getValue(), documentoChunkEdit));
        }

        /**
         * Elimina un chunk de documento del sistema.
         *
         * @param id Identificador del chunk a eliminar.
         * @return Un código HTTP 204 (NO CONTENT) indicando que la eliminación fue
         *         exitosa.
         */
        @Operation(summary = "Elimina un chunk de documento")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Chunk eliminado exitosamente", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Chunk no encontrado", content = @Content(mediaType = "application/json"))
        })
        @DeleteMapping("/chunk/{id}")
        public ResponseEntity<Void> delete(@PathVariable String id) {
                documentChunkService.delete(new IdEntityLong(id).getValue());
                return ResponseEntity.noContent().build();
        }

        /**
         * Obtiene una lista paginada de chunks de documento aplicando filtros mediante
         * parámetros en la URL.
         *
         * @param filter Lista de filtros opcionales.
         * @param page   Número de página a obtener.
         * @param size   Tamaño de la página.
         * @param sort   Criterios de ordenación.
         * @return Una respuesta paginada con la lista de chunks que cumplen los
         *         criterios y un código HTTP 200 (OK).
         * @throws FiltroException En caso de que los filtros aplicados no sean válidos.
         */
        @Operation(summary = "Obtiene una lista paginada de chunks de documento (método GET)", description = "Permite filtrar, paginar y ordenar la lista de chunks a través de parámetros en la URL.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de chunks obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Error en los filtros o parámetros proporcionados", content = @Content(mediaType = "application/json"))
        })
        @GetMapping("/chunks")
        public ResponseEntity<PaginaResponse<DocumentoChunkList>> getAllChunksGET(
                        @RequestParam(required = false) List<String> filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size,
                        @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
                return ResponseEntity.ok(documentChunkService.findAll(filter, page, size, sort));
        }

        /**
         * Obtiene una lista paginada de chunks de documento aplicando filtros mediante
         * un objeto de petición.
         *
         * @param peticionListadoFiltrado Objeto que contiene los criterios de filtrado
         *                                y paginación.
         * @return Una respuesta paginada con la lista de chunks que cumplen los
         *         criterios y un código HTTP 200 (OK).
         * @throws FiltroException En caso de que los filtros aplicados no sean válidos.
         */
        @Operation(summary = "Obtiene una lista paginada de chunks de documento (método POST)", description = "Permite obtener la lista de chunks aplicando filtros y paginación a través de un objeto de petición.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de chunks obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Error en los filtros o datos proporcionados", content = @Content(mediaType = "application/json"))
        })
        @PostMapping("/chunks")
        public ResponseEntity<PaginaResponse<DocumentoChunkList>> getAllChunksPOST(
                        @Valid @RequestBody PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
                return ResponseEntity.ok(documentChunkService.findAll(peticionListadoFiltrado));
        }
}
