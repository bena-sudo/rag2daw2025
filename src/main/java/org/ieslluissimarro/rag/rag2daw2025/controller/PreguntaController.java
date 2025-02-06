package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaEdit;
import org.ieslluissimarro.rag.rag2daw2025.srv.PreguntaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/rag/v1/")
public class PreguntaController {

    private final PreguntaService preguntaService;

    @Operation(summary = "Crea un nuevo registro de tipo pregunta en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Chat encontrado con éxito", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PreguntaEdit.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request: Error de validación en el ID proporcionado (errorCode='ID_FORMAT_INVALID')", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Not Found: No se encontró la pregunta con el ID proporcionado (errorCode='PREGUNTA_NOT_FOUND')", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    })
    @PostMapping("createQuestionChat")
    public ResponseEntity<PreguntaEdit> createPregunta(@Valid @RequestBody PreguntaEdit preguntaEdit,
            BindingResult bindingResult) {

        BindingResultHelper.validationBindingResult(bindingResult, "PREGUNTA_CREATE_VALIDATION");

        return ResponseEntity.status(HttpStatus.CREATED).body(preguntaService.create(
                preguntaEdit.getIdChat(), preguntaEdit.getTextoPregunta(), preguntaEdit.getUsuario()));
    }

   @GetMapping("answerQuestionChat")
    public ResponseEntity<PreguntaEdit> answerPregunta(
        @RequestParam(name="idQuestionchat") Long id_pregunta,
        @RequestParam(name="user") String user)
         {
        return ResponseEntity.ok(preguntaService.responderPreguntaChat(id_pregunta, user));
    }

}
