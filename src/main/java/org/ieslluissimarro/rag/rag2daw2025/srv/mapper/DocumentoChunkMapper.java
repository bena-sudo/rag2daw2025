package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DocumentoChunkMapper {
    DocumentoChunkMapper INSTANCE = Mappers.getMapper(DocumentoChunkMapper.class);

    DocumentoChunkEdit documentoChunkDBToDocumentoChunkEdit(DocumentoChunkDB documentoChunkDB);
    
    AlumnoEditDb alumnoEditToAlumnoEditDb(AlumnoEdit alumnoEdit);

    void updateAlumnoEditDbFromAlumnoEdit(AlumnoEdit alumnoEdit, @MappingTarget AlumnoEditDb alumnoEditDb);
}
