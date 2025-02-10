package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.exception.BindingResultErrorsResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaInfo;
import org.ieslluissimarro.rag.rag2daw2025.srv.PreguntaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequiredArgsConstructor
@RequestMapping("api/rag/v1/")
public class PreguntaController {
        public static String MENSAJE_INICIAL = "Mensaje inicial del rga";

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
                        @RequestParam(name = "idQuestionchat") Long id_pregunta,
                        @RequestParam(name = "user") String user) {
                return ResponseEntity.ok(preguntaService.responderPreguntaChat(id_pregunta, user));
        }

        @GetMapping("initialMessageChat")
        public ResponseEntity<String> initialMessagechat() {
                return ResponseEntity.ok(MENSAJE_INICIAL);
        }



        @Operation(summary = "Actualiza los datos de una pregunta existente en el sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK: Pregunta actualizada con éxito", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PreguntaEdit.class)) }),
                        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de validación en los datos proporcionados (errorCode='PREGUNTA_UPDATE_VALIDATION')", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = BindingResultErrorsResponse.class)) }),
                        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de validación en el ID proporcionado (errorCodes='ID_FORMAT_INVALID','ID_PREGUNTA_MISMATCH')", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
                        @ApiResponse(responseCode = "404", description = "Not Found: No se encontró el chat con el ID proporcionado (errorCode='PREGUNTA_NOT_FOUND_FOR_UPDATE')", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
                        @ApiResponse(responseCode = "409", description = "Conflict: Error al intentar actualizar un 'Chat' (errorCodes: 'FOREIGN_KEY_VIOLATION', 'UNIQUE_CONSTRAINT_VIOLATION', 'DATA_INTEGRITY_VIOLATION')", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
        })
        @PutMapping("/updatePregunta/{id}")
        public ResponseEntity<PreguntaEdit> update(@PathVariable Long id, @Valid @RequestBody PreguntaEdit preguntaEdit,
                        BindingResult bindingResult) {

                BindingResultHelper.validationBindingResult(bindingResult, "PREGUNTA_UPDATE_VALIDATION");

                return ResponseEntity.ok(preguntaService.update(id, preguntaEdit));
        }
}
