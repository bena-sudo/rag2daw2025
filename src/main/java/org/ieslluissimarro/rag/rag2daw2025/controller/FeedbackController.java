package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.FeedbackEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaEdit;
import org.ieslluissimarro.rag.rag2daw2025.srv.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
@RequestMapping("api/rag/v1/")
public class FeedbackController {


    private final FeedbackService feedbackService;


    @Operation(summary = "Crea un nuevo registro de tipo feedback en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Feedback encontrado con éxito", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FeedbackEdit.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request: Error de validación en el ID proporcionado (errorCode='ID_FORMAT_INVALID')", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Not Found: No se encontró la pregunta con el ID proporcionado (errorCode='PREGUNTA_NOT_FOUND')", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    })
    @PostMapping("feedbackAnsweredQuestionChat")
    public ResponseEntity<FeedbackEdit> createFeedback(@Valid @RequestBody FeedbackEdit feedbackEdit,
            BindingResult bindingResult) {

        BindingResultHelper.validationBindingResult(bindingResult, "PREGUNTA_CREATE_VALIDATION");

        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.create(
            feedbackEdit.getIdRespuesta(), feedbackEdit.getUsuario(), feedbackEdit.getFeedback()
        ));
    }

}
