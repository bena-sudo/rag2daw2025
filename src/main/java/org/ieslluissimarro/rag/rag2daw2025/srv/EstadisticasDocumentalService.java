package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.db.EstadisticaDocumentalDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EstadisticasDocumentalList;
import org.springframework.data.domain.Sort;

import jakarta.annotation.Nonnull;

public interface EstadisticasDocumentalService {
    /**
     * 
     * @param peticionListadoFiltrado Objeto que contiene los filtros de búsqueda y opciones de paginación.
     * @return Una respuesta con la lista paginada de documentos en formato DocAlumnoList.
     * @throws FiltroException Si ocurre un error en la aplicación de filtros.
     */
    PaginaResponse<EstadisticasDocumentalList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
    PaginaResponse<EstadisticasDocumentalList> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;

    public List<Object[]> getRevisionesPorFecha();
    public List<Object[]> countDocumentosPorEstado();
    public List<Object[]> getTiempoRevisionPromedioPorUsuario();

    
}
