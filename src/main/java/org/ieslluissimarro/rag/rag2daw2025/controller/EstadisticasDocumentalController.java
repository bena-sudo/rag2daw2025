package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EstadisticasDocumentalList;
import org.ieslluissimarro.rag.rag2daw2025.srv.EstadisticasDocumentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/estadistica")
public class EstadisticasDocumentalController {

    private final EstadisticasDocumentalService estadisticasService;

    public EstadisticasDocumentalController(EstadisticasDocumentalService estadisticasService) {
        this.estadisticasService = estadisticasService;
    }

    @GetMapping
    public ResponseEntity<PaginaResponse<EstadisticasDocumentalList>> getEstadisticasPaginadas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") List<String> sort) {
        PeticionListadoFiltrado peticion = new PeticionListadoFiltrado(null, page, size, sort);
        return ResponseEntity.ok(estadisticasService.findAll(peticion));
    }

    @GetMapping("/por-fecha")
    public ResponseEntity<List<Object[]>> getRevisionesPorFecha() {
        List<Object[]> resultados = estadisticasService.getRevisionesPorFecha();
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/por-estado")
    public ResponseEntity<List<Object[]>> countDocumentosPorEstado() {
        List<Object[]> resultados = estadisticasService.countDocumentosPorEstado();
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/tiempo-revision-promedio")
    public ResponseEntity<List<Object[]>> getTiempoRevisionPromedioPorUsuario() {
        List<Object[]> resultados = estadisticasService.countDocumentosPorEstado();
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<PaginaResponse<EstadisticasDocumentalList>> getAllEstadisticas(
            @RequestParam(required = false) List<String> filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
                return ResponseEntity.ok(estadisticasService.findAll(filter,page,size,sort));
    }

}
