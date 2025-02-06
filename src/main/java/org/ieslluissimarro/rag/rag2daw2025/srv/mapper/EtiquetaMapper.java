package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import org.ieslluissimarro.rag.rag2daw2025.model.db.EtiquetaDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EtiquetaMapper {
    EtiquetaMapper INSTANCE = Mappers.getMapper(EtiquetaMapper.class);

    EtiquetaDB etiquetaEditToEtiquetaDB(EtiquetaEdit etiquetaEdit);

    EtiquetaEdit etiquetaDBToEtiquetaEdit(EtiquetaDB etiquetaDB);

    EtiquetaList etiquetaDBToEtiquetaList(EtiquetaDB etiquetaDB);

    EtiquetaInfo etiquetaDBToEtiquetaInfo(EtiquetaDB etiquetaDB);

    void updateEtiquetaDBFrometiquetaEdit( EtiquetaEdit etiquetaEdit, @MappingTarget EtiquetaDB etiquetaDB);
}
