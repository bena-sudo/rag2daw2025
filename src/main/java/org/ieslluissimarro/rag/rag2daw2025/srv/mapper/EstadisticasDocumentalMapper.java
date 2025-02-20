package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.db.EstadisticaDocumentalDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EstadisticasDocumentalList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface EstadisticasDocumentalMapper {
    EstadisticasDocumentalMapper INSTANCE = Mappers.getMapper(EstadisticasDocumentalMapper.class);

    List<EstadisticasDocumentalList> estadisticasDocumentalDBToestadisticasDocumentalList(
            List<EstadisticaDocumentalDB> estadisticasDocumentalDBs);

    static PaginaResponse<EstadisticasDocumentalList> pageToPaginaResponseEstadisticasDocumentalList(
        Page<EstadisticaDocumentalDB> page, 
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
