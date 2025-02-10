package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.db.EstadisticasDocumentalDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EstadisticasDocumentalList;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

public interface EstadisticasDocumentalMapper {
    EstadisticasDocumentalMapper INSTANCE = Mappers.getMapper(EstadisticasDocumentalMapper.class);

    List<EstadisticasDocumentalList> estadisticasDocumentalDBToestadisticasDocumentalList(
            List<EstadisticasDocumentalDB> estadisticasDocumentalDBs);

    static PaginaResponse<EstadisticasDocumentalList> pageToPaginaResponseEstadisticasDocumentalList(
        Page<EstadisticasDocumentalDB> page, 
        List<FiltroBusqueda> filtros, 
        List<String> ordenaciones){
        return new PaginaResponse<>(
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            INSTANCE.estadisticasDocumentalDBToestadisticasDocumentalList(page.getContent()),
            filtros,
            ordenaciones
        );
    }
}
