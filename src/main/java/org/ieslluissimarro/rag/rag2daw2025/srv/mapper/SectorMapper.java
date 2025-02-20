package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.db.SectorDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.SectorEdit;
import org.springframework.data.domain.Page;

@Mapper
public interface SectorMapper {
    SectorMapper INSTANCE = Mappers.getMapper(SectorMapper.class);

    SectorEdit sectorDbSectorEdit(SectorDb sectorDb);
    SectorDb sectorEditSectorDb(SectorEdit sectorEdit);
    void updateSectorDbFromSectorEdit(SectorEdit sectorEdit ,  @MappingTarget SectorDb sectorDb);

    List<SectorEdit> sectorDbToSectorEdit(List<SectorDb> sectorDb);

    static PaginaResponse<SectorEdit> pageToPaginaResponseSectorEdit(
            Page<SectorDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.sectorDbToSectorEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}
