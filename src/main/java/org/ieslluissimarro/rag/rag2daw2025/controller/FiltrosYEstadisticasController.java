package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.Map;

import org.ieslluissimarro.rag.rag2daw2025.helper.JsonToMapHelper;
import org.ieslluissimarro.rag.rag2daw2025.helper.SQLHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.FiltrosRequest;
import org.ieslluissimarro.rag.rag2daw2025.srv.FiltrosYEstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
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
        {
            filtro:null|fecha2,
        }

     * Ejemplo de las llamadas que recibirán los endpoints
     */



    @PostMapping("chats/filter")
    public ResponseEntity<?> getDatosFiltradosChat(@RequestBody FiltrosRequest parametros) {
        try {

            String query = SQLHelper.builderSentencias("chats", JsonToMapHelper.converter(parametros));

            return ResponseEntity.ok().body(service.executeQuery(query));

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ILLEGAL_ARGUMENT_EXCEPTION");
        }

    }
    

    @GetMapping("preguntas/filter")
    public ResponseEntity<?> getDatosFiltradosPreguntas(@RequestParam Map<String,String> parametros) {
        try {
            String query = SQLHelper.builderSentencias("preguntas", parametros);

            return ResponseEntity.ok().body(service.executeQuery(query));

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ILLEGAL_ARGUMENT_EXCEPTION");
        }

    }

    @GetMapping("contexto/popular")
    public ResponseEntity<?> getListaContextos() {
        try {
            String query = SQLHelper.selectContextoCountGrouped("chats" );

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
