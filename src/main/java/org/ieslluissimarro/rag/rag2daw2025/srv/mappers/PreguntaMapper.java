package org.ieslluissimarro.rag.rag2daw2025.srv.mappers;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PreguntaMapper {
    PreguntaMapper INSTANCE = Mappers.getMapper(PreguntaMapper.class);

    PreguntaInfo PreguntaDbAPreguntaInfo(PreguntaDb preguntaDb);
    PreguntaList PreguntaDbAPreguntaList(PreguntaDb preguntaDb);
    PreguntaEdit PreguntaDbAPreguntaEdit(PreguntaDb preguntaDb);
    PreguntaDb PreguntaEditAPreguntaDb(PreguntaEdit preguntaEdit);
    List<PreguntaList> preguntasAPreguntaList(List<PreguntaDb> preguntasDb);
}
