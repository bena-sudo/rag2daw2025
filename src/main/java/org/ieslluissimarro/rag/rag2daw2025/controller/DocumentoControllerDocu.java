package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoEditDocu;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoNew;
import org.ieslluissimarro.rag.rag2daw2025.srv.DocumentoServiceDocu;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("/api/v2")
@CrossOrigin()
public class DocumentoControllerDocu {

    private final DocumentoServiceDocu documentoService;

    /**
     * Crea un nuevo documento en el sistema.
     * <p>
     * Este endpoint recibe la información del documento en formato
     * multipart/form-data,
     * lo que permite enviar tanto datos como archivos.
     * </p>
     *
     * @param documentoNew  Objeto que contiene los datos del documento a crear.
     *                      Debe cumplir con las validaciones definidas.
     * @param bindingResult Resultado de las validaciones de los datos
     *                      proporcionados.
     * @return Un objeto DocumentoNew con los datos del documento creado y un código
     *         HTTP 201 (CREATED).
     */
    @Operation(summary = "Crea un nuevo documento en el sistema", description = "Recibe datos en formato multipart/form-data para crear un documento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Documento creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoNew.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos proporcionados", content = @Content(mediaType = "application/json"))
    })
    @PreAuthorize("@authorizationService.hasPermission('CREAR_DOCUMENTO')")
    @PostMapping(value = "/documento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentoNew> create(
            @Valid @ModelAttribute DocumentoNew documentoNew,
            BindingResult bindingResult) {

        BindingResultHelper.validateBindingResult(bindingResult, "DOCUMENT_CREATE_VALIDATION");

        return ResponseEntity.status(HttpStatus.CREATED).body(documentoService.create(documentoNew));
    }

    /**
     * Obtiene la información de un documento a partir de su identificador.
     *
     * @param id Identificador del documento a buscar.
     * @return Un objeto DocumentoInfo con los datos del documento y un código HTTP
     *         200 (OK).
     */
    @Operation(summary = "Obtiene la información de un documento por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoInfo.class))),
            @ApiResponse(responseCode = "404", description = "Documento no encontrado", content = @Content(mediaType = "application/json"))
    })
    @PreAuthorize("@authorizationService.hasPermission('VER_DOCUMENTOS')")
    @GetMapping("/documento/{id}")
    public ResponseEntity<DocumentoInfo> read(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.read(id));
    }

    /**
     * Actualiza los datos de un documento existente.
     *
     * @param id            Identificador del documento a actualizar.
     * @param documentoEdit Objeto que contiene los datos actualizados del
     *                      documento.
     * @param bindingResult Resultado de las validaciones de los datos
     *                      proporcionados.
     * @return Un objeto DocumentoEdit con los datos actualizados y un código HTTP
     *         200 (OK).
     */
    @Operation(summary = "Actualiza los datos de un documento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoEditDocu.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos proporcionados", content = @Content(mediaType = "application/json"))
    })
    @PreAuthorize("@authorizationService.hasPermission('EDITAR_DOCUMENTO')")
    @PutMapping("/documento/{id}")
    public ResponseEntity<DocumentoEditDocu> update(@PathVariable Long id, @Valid @RequestBody DocumentoEditDocu documentoEdit,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "DOCUMENT_UPDATE_VALIDATION");
        return ResponseEntity.ok(documentoService.update(id, documentoEdit));
    }

    /**
     * Elimina un documento del sistema.
     *
     * @param id Identificador del documento a eliminar.
     * @return Un código HTTP 204 (NO CONTENT) si la eliminación es exitosa.
     */
    @Operation(summary = "Elimina un documento del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Documento eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Documento no encontrado", content = @Content(mediaType = "application/json"))
    })
    @PreAuthorize("@authorizationService.hasPermission('ELIMINAR_DOCUMENTO')")
    @DeleteMapping("/documento/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una lista paginada de documentos aplicando filtros mediante
     * parámetros en la URL.
     *
     * @param filter Lista de filtros opcionales.
     * @param page   Número de página a obtener.
     * @param size   Tamaño de la página.
     * @param sort   Criterios de ordenación.
     * @return Una respuesta paginada con la lista de documentos que cumplen los
     *         criterios y un código HTTP 200 (OK).
     * @throws FiltroException En caso de que los filtros aplicados no sean válidos.
     */
    @Operation(summary = "Obtiene una lista paginada de documentos (método GET)", description = "Permite filtrar, paginar y ordenar la lista de documentos mediante parámetros en la URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de documentos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error en los filtros o parámetros proporcionados", content = @Content(mediaType = "application/json"))
    })
    @PreAuthorize("@authorizationService.hasPermission('VER_DOCUMENTOS')")
    @GetMapping("/documentos")
    public ResponseEntity<PaginaResponse<DocumentoList>> getAllDocumentosGET(
            @RequestParam(required = false) List<String> filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(documentoService.findAll(filter, page, size, sort));
    }

    /**
     * Obtiene una lista paginada de documentos aplicando filtros mediante un objeto
     * de petición.
     *
     * @param peticionListadoFiltrado Objeto que contiene los criterios de filtrado
     *                                y paginación.
     * @return Una respuesta paginada con la lista de documentos que cumplen los
     *         criterios y un código HTTP 200 (OK).
     * @throws FiltroException En caso de que los filtros aplicados no sean válidos.
     */
    @Operation(summary = "Obtiene una lista paginada de documentos (método POST)", description = "Permite obtener la lista de documentos aplicando filtros y paginación a través de un objeto de petición.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de documentos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error en los filtros o datos proporcionados", content = @Content(mediaType = "application/json"))
    })
    @PreAuthorize("@authorizationService.hasPermission('VER_DOCUMENTOS')")
    @PostMapping("/documentos")
    public ResponseEntity<PaginaResponse<DocumentoList>> getAllDocumentosPOST(
            @Valid @RequestBody PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        return ResponseEntity.ok(documentoService.findAll(peticionListadoFiltrado));
    }
}