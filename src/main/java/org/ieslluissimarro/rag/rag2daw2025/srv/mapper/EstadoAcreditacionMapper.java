package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.ieslluissimarro.rag.rag2daw2025.model.db.EstadoAcreditacionDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EstadoAcreditacionEdit;

import java.util.List;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.springframework.data.domain.Page;

@Mapper
public interface EstadoAcreditacionMapper {
    EstadoAcreditacionMapper INSTANCE = Mappers.getMapper(EstadoAcreditacionMapper.class);

    @Mapping(source = "modulo.id", target = "modulo_id", defaultExpression = "java(estadoAcreditacionDb.getModulo() != null ? estadoAcreditacionDb.getModulo().getId() : null)")
    EstadoAcreditacionEdit estadoAcreditacionDbEstadoAcreditacionEdit(EstadoAcreditacionDb estadoAcreditacionDb);

    @Mapping(target = "modulo", expression = "java(estadoAcreditacionEdit.getModulo_id() != null ? new ModuloDb(estadoAcreditacionEdit.getModulo_id()) : null)")
    EstadoAcreditacionDb estadoAcreditacionEditEstadoAcreditacionDb(EstadoAcreditacionEdit estadoAcreditacionEdit);

    void updateEstadoAcreditacionDbFromEstadoAcreditacionEdit(EstadoAcreditacionEdit estadoAcreditacionEdit ,  @MappingTarget EstadoAcreditacionDb estadoAcreditacionDb);

    List<EstadoAcreditacionEdit> estadoAcreditacionDbToEstadoAcreditacionEdit(List<EstadoAcreditacionDb> estadoAcreditacionDb);

    static PaginaResponse<EstadoAcreditacionEdit> pageToPaginaResponseEstadoAcreditacionEdit(
            Page<EstadoAcreditacionDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.estadoAcreditacionDbToEstadoAcreditacionEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}