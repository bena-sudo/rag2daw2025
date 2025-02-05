package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatEdit;
import org.ieslluissimarro.rag.rag2daw2025.srv.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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



@Controller
@RequiredArgsConstructor
@RequestMapping("api/rag/v1/")
public class ChatController {

    public static  String MENSAJE_INICIAL = "Mensaje inicial del rga";

    private final ChatService chatService;

    @Operation(summary = "Crea un nuevo registro de tipo chat en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Chat encontrado con éxito", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ChatEdit.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request: Error de validación en el ID proporcionado (errorCode='ID_FORMAT_INVALID')", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Not Found: No se encontró el Chat con el ID proporcionado (errorCode='CHAT_NOT_FOUND')", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    })
    @PostMapping
    public ResponseEntity<ChatEdit> createChat(@Valid @RequestBody ChatEdit chatEdit, BindingResult bindingResult) {

        BindingResultHelper.validationBindingResult(bindingResult, "CHAT_CREATE_VALIDATION");

        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.create(chatEdit));
    }




    @GetMapping("initialMessageChat")
    public ResponseEntity<ChatEdit> initialMessagechat() {
        return ResponseEntity.ok(chatService.initialMessageChat(MENSAJE_INICIAL));
    }
}
