package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.ieslluissimarro.rag.rag2daw2025.exception.BindingResultErrorsResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.helper.FiltroBusquedaQualitat;
import org.ieslluissimarro.rag.rag2daw2025.helper.FiltroBusquedaHelper;
import org.ieslluissimarro.rag.rag2daw2025.helper.PaginationHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ListadoRespuesta;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaDto;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaInfo;
import org.ieslluissimarro.rag.rag2daw2025.srv.ChatService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import reactor.core.publisher.Flux;

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

    @PreAuthorize("@authorizationService.hasPermission('CREAR_CHAT')")
    @PostMapping("createChat")
    public ResponseEntity<ChatInfo> createChat(@Valid @RequestBody ChatList chatEdit, BindingResult bindingResult) {

        BindingResultHelper.validateBindingResult(bindingResult, "CHAT_CREATE_VALIDATION");

        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.create(chatEdit));
    }
    @PreAuthorize("@authorizationService.hasPermission('VER_CHATS')")
    @GetMapping("returnChats")
    public ResponseEntity<ListadoRespuesta<ChatList>> devuelveListaDeChatList(
            @RequestParam(required = false) String[] filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "idChat,asc") String[] sort) throws FiltroException {

        List<FiltroBusquedaQualitat> listarFiltros = FiltroBusquedaHelper.createFiltroBusqueda(filter);

        Pageable paging = PaginationHelper.createPageable(page, size, sort);

        PaginaDto<ChatList> paginaChatInfo = chatService.findAllPageChatInfo(listarFiltros, paging);

        ListadoRespuesta<ChatList> respuesta = new ListadoRespuesta<>(
                paginaChatInfo.getNumber(),
                paginaChatInfo.getSize(),
                paginaChatInfo.getTotalElements(),
                paginaChatInfo.getTotalPages(),
                paginaChatInfo.getContent());

        return ResponseEntity.ok(respuesta);
    }
    @PreAuthorize("@authorizationService.hasPermission('VER_CHATS')")
    @GetMapping("returnChatInfo")
    public ResponseEntity<ChatInfo> devuelveChatInfo(@RequestParam Long idChat) {
        return ResponseEntity.ok(chatService.findById(idChat));
    }

    @Operation(summary = "Devuelve un listado de PreguntaInfo dado un idChat.")
    @PreAuthorize("@authorizationService.hasPermission('VER_PREGUNTAS')")
    @GetMapping("returnPreguntasByIdChat")
    public ResponseEntity<List<PreguntaInfo>> devuelvePreguntasByIdChat(@RequestParam Long idChat) {

        return ResponseEntity.ok(chatService.getChatPreguntasByIdChat(idChat));
    }
    @PreAuthorize("@authorizationService.hasPermission('ELIMINAR_CHAT')")
    @DeleteMapping("deleteChat")
    public ResponseEntity<Void> delete(@RequestParam Long idChat) {
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
    @PreAuthorize("@authorizationService.hasPermission('MODIFICAR_CHAT')")
    @PutMapping("/updateChat/{id}")
    public ResponseEntity<ChatEdit> update(@PathVariable Long id, @Valid @RequestBody ChatEdit chatEdit,
            BindingResult bindingResult) {

        BindingResultHelper.validateBindingResult(bindingResult, "CHAT_UPDATE_VALIDATION");

        return ResponseEntity.ok(chatService.update(id, chatEdit));
    }
    @GetMapping(value = "/reply/{idPregunta}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Character> responderPregunta(@PathVariable Long idPregunta) {
        String respuesta = "Simulación de respuesta. Esto es una simulación para comprobar si realmente funciona el flujo que hemos incorporado en el backend y el SSE que hay en el frontend.";

        List<Character> caracteres = respuesta.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        // Agregar el carácter especial al final
        caracteres.add('\u0003');

        return Flux.fromIterable(caracteres)
                .delayElements(Duration.ofMillis(50));

    }

}
