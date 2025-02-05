package org.ieslluissimarro.rag.rag2daw2025.srv.mappers;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentChunksDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentChunksEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentChunksInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentChunksList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DocumentChunksMapper {
    DocumentChunksMapper INSTANCE = Mappers.getMapper(DocumentChunksMapper.class);

    DocumentChunksInfo documentChunksDbToDocumentChunksInfo(DocumentChunksDb DocumentChunksDb);
    DocumentChunksList DocumentChunksDbADocumentChunksList(DocumentChunksDb DocumentChunksDb);
    DocumentChunksEdit DocumentChunksDbADocumentChunksEdit(DocumentChunksDb DocumentChunksDb);
    DocumentChunksDb DocumentChunksEditADocumentChunksDb(DocumentChunksEdit DocumentChunksEdit);
    List<DocumentChunksList> DocumentChunkssADocumentChunksList(List<DocumentChunksDb> DocumentChunkssDb);
}
