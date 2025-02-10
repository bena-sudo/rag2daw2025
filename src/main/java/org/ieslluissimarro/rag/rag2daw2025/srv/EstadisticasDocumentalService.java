package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EstadisticasDocumentalList;

public interface EstadisticasDocumentalService {
    /**
     * 
     * @param peticionListadoFiltrado Objeto que contiene los filtros de búsqueda y opciones de paginación.
     * @return Una respuesta con la lista paginada de documentos en formato DocAlumnoList.
     * @throws FiltroException Si ocurre un error en la aplicación de filtros.
     */
    PaginaResponse<EstadisticasDocumentalList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;

    List<Object[]> getRevisionesPorFecha();
    List<Object[]> countDocumentosPorEstado();
    List<Object[]> getTiempoRevisionPromedioPorUsuario();

}
