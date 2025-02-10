package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentoDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoNew;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface DocumentoMapper {
    
    DocumentoMapper INSTANCE = Mappers.getMapper(DocumentoMapper.class);

    DocumentoEdit documentoDBToDocumentoEdit(DocumentoDB documentoDb);

    DocumentoInfo documentoDBToDocumentoInfo(DocumentoDB documentoDb);

    DocumentoList documentoDBToDocumentoList(DocumentoDB documentoDb);

    DocumentoNew documentoDBToDocumentoNew(DocumentoDB documentoDb);

    DocumentoDB documentoNewToDocumentoDB(DocumentoNew documentoNew);

    List<DocumentoList> documentoDBToDocumentoList(List<DocumentoDB> documentoDb);

    void updateDocumentoDBFromDocumentoEdit(DocumentoEdit documentoEdit, @MappingTarget DocumentoDB documentoDb);

    static PaginaResponse<DocumentoList> pageToPaginaResponseDocumentoList(
            Page<DocumentoDB> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.documentoDBToDocumentoList(page.getContent()),
                filtros,
                ordenaciones);
    }

}
