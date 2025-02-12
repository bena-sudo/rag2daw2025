package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;
import java.util.Map;

import org.ieslluissimarro.rag.rag2daw2025.helper.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.helper.FiltroBusquedaHelper;
import org.ieslluissimarro.rag.rag2daw2025.helper.JsonToMapHelper;
import org.ieslluissimarro.rag.rag2daw2025.helper.PaginationHelper;
import org.ieslluissimarro.rag.rag2daw2025.helper.SQLHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.FiltrosRequest;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ListadoRespuesta;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaDto;
import org.ieslluissimarro.rag.rag2daw2025.srv.FiltrosYEstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("api/rag/v1/")
public class FiltrosYEstadisticasController {

    @Autowired
    private FiltrosYEstadisticasService service;

    /*
     * Ejemplo de json que recibe el endpoint filter
     * Los campos enviados serán los unicos en ser filtrados, por lo que solo envies
     * los campos que quieres que se apliquen.
     * Si no quieres que se filtre por un campo no lo envies en el json ni pongas
     * como valor null (excepto en las fechas donde si que aceptan null).
     * {
     * filterUser : "valor" ,
     * filterRango:YYYY-MM-DD HH:MM:SS|YYYY-MM-DD HH:MM:SS, los campos pueden ser
     * valores "null" y obtendrán valores por defecto
     * filterPregunta: "valor",
     * filterRespuesta: "valor",
     * filterFeedback: "valor", los valores normalmente son BIEN, NORMAL y MAL
     * filterValorado: true/false,
     * filterChunks: "valor"
     * 
     * }
     * 
     * 
     */

    @PostMapping("filter")
    public ResponseEntity<ListadoRespuesta<ChatList>> getDatosByFiltroRequest(@RequestBody FiltrosRequest parametros,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "idChat,asc") String[] sort) {

        Pageable paging = PaginationHelper.createPageable(page, size, sort);

        String query = SQLHelper.builderSentencias(JsonToMapHelper.converter(parametros));

        PaginaDto<ChatList> paginaChatInfo = service.executePaginatedQuery(query, paging);

        ListadoRespuesta<ChatList> respuesta = new ListadoRespuesta<>(
                paginaChatInfo.getNumber(),
                paginaChatInfo.getSize(),
                paginaChatInfo.getTotalElements(),
                paginaChatInfo.getTotalPages(),
                paginaChatInfo.getContent());

        return ResponseEntity.ok().body(respuesta);

    }

    /*
     * @PostMapping("chats/filter")
     * public ResponseEntity<?> getDatosFiltradosChat(@RequestBody FiltrosRequest
     * parametros) {
     * try {
     * 
     * String query = SQLHelper.builderSentencias("chats",
     * JsonToMapHelper.converter(parametros));
     * 
     * return ResponseEntity.ok().body(service.executeQuery(query));
     * 
     * } catch (IllegalArgumentException e) {
     * throw new IllegalArgumentException("ILLEGAL_ARGUMENT_EXCEPTION");
     * }
     * 
     * }
     * 
     * 
     * @GetMapping("preguntas/filter")
     * public ResponseEntity<?> getDatosFiltradosPreguntas(@RequestParam
     * Map<String,String> parametros) {
     * try {
     * String query = SQLHelper.builderSentencias("preguntas", parametros);
     * 
     * return ResponseEntity.ok().body(service.executeQuery(query));
     * 
     * } catch (IllegalArgumentException e) {
     * throw new IllegalArgumentException("ILLEGAL_ARGUMENT_EXCEPTION");
     * }
     * 
     * }
     */
    @GetMapping("contexto/popular")
    public ResponseEntity<?> getListaContextos() {
        try {
            String query = SQLHelper.selectContextoCountGrouped("chats");

            return ResponseEntity.ok().body(service.executeQuery(query));

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ILLEGAL_ARGUMENT_EXCEPTION");
        }

    }

    @GetMapping("getListUsuarios")
    public ResponseEntity<?> getListUsuarios() {
        try {
            String query = SQLHelper.selectDistinctString("chats", "\"user\"");

            return ResponseEntity.ok().body(service.executeQuery(query));

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ILLEGAL_ARGUMENT_EXCEPTION");
        }

    }
}
