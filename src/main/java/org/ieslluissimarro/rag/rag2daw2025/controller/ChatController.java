package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.BindingResultErrorsResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaEdit;
import org.ieslluissimarro.rag.rag2daw2025.srv.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/rag/v1/")
public class ChatController {

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
    @PostMapping("createChat")
    public ResponseEntity<ChatInfo> createChat(@Valid @RequestBody ChatEdit chatEdit, BindingResult bindingResult) {

        BindingResultHelper.validationBindingResult(bindingResult, "CHAT_CREATE_VALIDATION");

        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.create(chatEdit));
    }

    @GetMapping("returnChats")
    public ResponseEntity<List<ChatList>> devuelveListaDeChatList(@RequestParam String param) {
        return ResponseEntity.ok(chatService.findAllChatList());
    }

    @DeleteMapping("deleteChat")
    public ResponseEntity<Void> delete(@RequestParam Long idChat) {
        // alumnoService.delete(new DniString(dni).getValue());
        chatService.delete(idChat);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualiza los datos de un chat existente en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Chat actualizado con éxito", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PreguntaEdit.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request: Errores de validación en los datos proporcionados (errorCode='CHAT_UPDATE_VALIDATION')", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BindingResultErrorsResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request: Errores de validación en el ID proporcionado (errorCodes='ID_FORMAT_INVALID','ID_CHAT_MISMATCH')", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Not Found: No se encontró el Chat con el ID proporcionado (errorCode='CHAT_NOT_FOUND_FOR_UPDATE')", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
            @ApiResponse(responseCode = "409", description = "Conflict: Error al intentar actualizar un 'Chat' (errorCodes: 'FOREIGN_KEY_VIOLATION', 'UNIQUE_CONSTRAINT_VIOLATION', 'DATA_INTEGRITY_VIOLATION')", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    })
    @PutMapping("/updateChat/{id}")
    public ResponseEntity<ChatEdit> update(@PathVariable Long id, @Valid @RequestBody ChatEdit chatEdit,
            BindingResult bindingResult) {

        BindingResultHelper.validationBindingResult(bindingResult, "CHAT_UPDATE_VALIDATION");

        return ResponseEntity.ok(chatService.update(id, chatEdit));
    }

}
