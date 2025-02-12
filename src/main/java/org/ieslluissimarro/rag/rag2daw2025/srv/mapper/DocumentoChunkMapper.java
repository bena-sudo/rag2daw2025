package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentoChunkDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface DocumentoChunkMapper {
    DocumentoChunkMapper INSTANCE = Mappers.getMapper(DocumentoChunkMapper.class);

    DocumentoChunkEdit documentoChunkDBToDocumentoChunkEdit(DocumentoChunkDB documentoChunkDB);
    
    DocumentoChunkDB documentoChunkEditToDocumentoChunkDB(DocumentoChunkEdit documentoChunkEdit);

    DocumentoChunkInfo documentoChunkDBToDocumentoChunkInfo(DocumentoChunkDB documentoChunkDB);

    List<DocumentoChunkList> documentoChunkDBToDocumentoChunkList(List<DocumentoChunkDB> documentoChunkDB);

    void updateDocumentoChunkDBFromDocumentoChunkEdit(DocumentoChunkEdit documentoChunkEdit, @MappingTarget DocumentoChunkDB documentoChunkDB);

    static PaginaResponse<DocumentoChunkList> pageToPaginaResponseDocumentoChunkList(
            Page<DocumentoChunkDB> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.documentoChunkDBToDocumentoChunkList(page.getContent()),
                filtros,
                ordenaciones);
    }
}
