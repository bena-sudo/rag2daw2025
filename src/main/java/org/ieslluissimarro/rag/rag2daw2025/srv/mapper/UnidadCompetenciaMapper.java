package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UnidadCompetenciaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UnidadCompetenciaEdit;
import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.springframework.data.domain.Page;

@Mapper
public interface UnidadCompetenciaMapper {

    UnidadCompetenciaMapper INSTANCE = Mappers.getMapper(UnidadCompetenciaMapper.class);
   
    @Mapping(target = "modulo_id", source = "modulo.id")
    UnidadCompetenciaEdit unidadCompetenciaDbToUnidadCompetenciaEdit(UnidadCompetenciaDb unidadCompetenciaDb);

    @Mapping(target = "modulo.id", source = "modulo_id")
    UnidadCompetenciaDb unidadCompetenciaEditToUnidadCompetenciaDb(UnidadCompetenciaEdit unidadCompetenciaEdit);
    
    void updateAlumnoDbFromAlumnoEdit(UnidadCompetenciaEdit unidadCompetenciaEdit,  @MappingTarget UnidadCompetenciaDb unidadCompetenciaDb);

    List<UnidadCompetenciaEdit> unidadCompetenciaDbToUnidadCompetenciaEdit(List<UnidadCompetenciaDb> unidadCompetenciaDb);

    static PaginaResponse<UnidadCompetenciaEdit> pageToPaginaResponseUnidadCompetenciaEdit(
            Page<UnidadCompetenciaDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.unidadCompetenciaDbToUnidadCompetenciaEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}