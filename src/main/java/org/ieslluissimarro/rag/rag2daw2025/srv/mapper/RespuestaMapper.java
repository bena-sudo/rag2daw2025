package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.ieslluissimarro.rag.rag2daw2025.model.db.RespuestaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.RespuestaEdit;
import java.util.List;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.springframework.data.domain.Page;

@Mapper
public interface RespuestaMapper {
    RespuestaMapper INSTANCE = Mappers.getMapper(RespuestaMapper.class);

    @Mapping(target = "pregunta_id", source = "pregunta.id")
    @Mapping(target = "usuario_id", source = "usuario.id")
    RespuestaEdit respuestaDbRespuestaEdit(RespuestaDb respuestaDb);

    
    @Mapping(target = "pregunta.id", source = "pregunta_id")
    @Mapping(target = "usuario.id", source = "usuario_id")
    RespuestaDb respuestaEditRespuestaDb(RespuestaEdit respuestaEdit);


    void updateRespuestaDbFromRespuestaEdit(RespuestaEdit respuestaEdit ,  @MappingTarget RespuestaDb respuestaDb);

    List<RespuestaEdit> respuestaDbToRespuestaEdit(List<RespuestaDb> respuestaDb);
    
    List<RespuestaDb> respuestaEditToRespuestaDb(List<RespuestaEdit> respuestaEdit);


    static PaginaResponse<RespuestaEdit> pageToPaginaResponseRespuestaEdit(
            Page<RespuestaDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.respuestaDbToRespuestaEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}