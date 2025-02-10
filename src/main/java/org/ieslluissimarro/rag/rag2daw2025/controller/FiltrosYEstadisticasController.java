package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.Map;

import org.ieslluissimarro.rag.rag2daw2025.helper.SQLHelper;
import org.ieslluissimarro.rag.rag2daw2025.srv.FiltrosYEstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("api/rag/v1/")
public class FiltrosYEstadisticasController {

    @Autowired
    private FiltrosYEstadisticasService service;


    /* 
     * Ejemplo de las llamadas que recibirán los endpoints
     */
    //http.....com/api/rag/v1/chats/filter?operacion=filtro&filterBy=user&valor1=manolo
    //http.....com/api/rag/v1/chats/filter?operacion=rango&filterBy=fecha&valor1=2024-02-01  &valor2=2025-03-01     mostrar los chats en el rango de valor1 y valor2
    //http.....com/api/rag/v1/chats/filter?operacion=rango&filterBy=fecha&valor1=2024-02-01       aqui falta el valor2 por lo que será default el dia actual
    //http.....com/api/rag/v1/chats/filter?operacion=count&filterBy=user&valor1=pepe        recuento simple


    @GetMapping("chats/filter")
    public ResponseEntity<?> getDatosFiltradosChat(@RequestParam Map<String,String> parametros) {
        try {
            String query = SQLHelper.builderSentencias("chats", parametros);

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
