package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.db.CuestionarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.CuestionarioEdit;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.springframework.data.domain.Page;

@Mapper
public interface CuestionarioMapper {
    CuestionarioMapper INSTANCE = Mappers.getMapper(CuestionarioMapper.class);

    @Mapping(source = "unidadCompetencia.id", target = "unidad_competencia_id")
    CuestionarioEdit cuestionarioDbCuestionarioEdit(CuestionarioDb cuestionarioDb);
    
    @Mapping(source = "unidad_competencia_id", target = "unidadCompetencia.id")
    CuestionarioDb cuestionarioEditCuestionarioDb(CuestionarioEdit cuestionarioEdit);
    
    void updateCuestionarioDbFromCuestionarioEdit(CuestionarioEdit cuestionarioEdit ,  @MappingTarget CuestionarioDb cuestionarioDb);

    List<CuestionarioEdit> cuestionarioDbToCuestionarioEdit(List<CuestionarioDb> cuestionarioDb);

    static PaginaResponse<CuestionarioEdit> pageToPaginaResponseCuestionarioEdit(
            Page<CuestionarioDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.cuestionarioDbToCuestionarioEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}