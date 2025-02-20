package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.IdEntityLong;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkList;
import org.ieslluissimarro.rag.rag2daw2025.srv.RagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rag")
public class RagController {

    private final RagService ragService;

    /**
     * Endpoint para subir un documento al sistema RAG.
     *
     * @param id        Identificador del documento a subir.
     * @param idUsuario Identificador del usuario que realiza la operación.
     * @return Una lista de objetos DocumentoChunkList representando los chunks del
     *         documento, junto con el código HTTP 200 (OK).
     */
    @Operation(summary = "Sube un documento al sistema RAG y obtiene los chunks generados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento subido exitosamente y chunks generados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoChunkList.class))),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/subirDocumento/{id}/{idUsuario}")
    public ResponseEntity<List<DocumentoChunkList>> subir(@PathVariable String id, @PathVariable String idUsuario) {
        return ResponseEntity.ok(ragService.subirDoc(new IdEntityLong(id).getValue()/* , usuario */));
    }

    /**
     * Endpoint para confirmar un chunk de documento en el sistema RAG.
     *
     * @param id        Identificador del chunk a confirmar.
     * @param idUsuario Identificador del usuario que realiza la operación.
     * @return Un objeto DocumentoChunkInfo con la información del chunk confirmado,
     *         junto con el código HTTP 200 (OK).
     */
    @Operation(summary = "Confirma un chunk de documento en el sistema RAG")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chunk confirmado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentoChunkInfo.class))),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/confirmChunk/{id}/{idUsuario}")
    public ResponseEntity<DocumentoChunkInfo> confirmar(@PathVariable String id, @PathVariable String idUsuario) {
        return ResponseEntity.ok(ragService.confirmarChunk(new IdEntityLong(id).getValue()));
    }
}
