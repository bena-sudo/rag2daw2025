package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.db.EtiquetaDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface EtiquetaMapper {
    EtiquetaMapper INSTANCE = Mappers.getMapper(EtiquetaMapper.class);

    EtiquetaDB etiquetaEditToEtiquetaDB(EtiquetaEdit etiquetaEdit);

    EtiquetaEdit etiquetaDBToEtiquetaEdit(EtiquetaDB etiquetaDB);

    EtiquetaList etiquetaDBToEtiquetaList(EtiquetaDB etiquetaDB);

    EtiquetaInfo etiquetaDBToEtiquetaInfo(EtiquetaDB etiquetaDB);

    void updateEtiquetaDBFrometiquetaEdit( EtiquetaEdit etiquetaEdit, @MappingTarget EtiquetaDB etiquetaDB);

    List<EtiquetaList> etiquetasDBToEtiquetasList(List<EtiquetaDB> etiquetaDBs);

    static PaginaResponse<EtiquetaList> pageToPaginaResponseEtiquetaList(
            Page<EtiquetaDB> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.etiquetasDBToEtiquetasList(page.getContent()),
                filtros,
                ordenaciones);
    }
}
