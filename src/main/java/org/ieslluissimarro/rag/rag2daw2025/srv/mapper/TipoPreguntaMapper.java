package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import org.mapstruct.Mapper;
import org.ieslluissimarro.rag.rag2daw2025.model.db.TipoPreguntaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.TipoPreguntaEdit;

@Mapper
public interface TipoPreguntaMapper {
    TipoPreguntaMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TipoPreguntaMapper.class);

    TipoPreguntaEdit tipoPreguntaDbToTipoPreguntaEdit(TipoPreguntaDb tipoPreguntaDb);

    TipoPreguntaDb tipoPreguntaDtoToTipoPreguntaDb(TipoPreguntaEdit tipoPreguntaDto);
}
