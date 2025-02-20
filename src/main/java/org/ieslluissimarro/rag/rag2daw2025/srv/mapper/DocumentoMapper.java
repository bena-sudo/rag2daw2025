package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentoDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoEdit;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.springframework.data.domain.Page;

@Mapper
public interface DocumentoMapper {
    DocumentoMapper INSTANCE = Mappers.getMapper(DocumentoMapper.class);

    
    DocumentoEdit documentoDbToDocumentoEdit(DocumentoDb documentoDb);
    
    
    DocumentoDb documentoEditToDocumentoDb(DocumentoEdit documentoEdit);
    
    void updateDocumentoDbFromDocumentoEdit(DocumentoEdit documentoEdit ,  @MappingTarget DocumentoDb documentoDb);

    List<DocumentoEdit> documentoDbToDocumentoEdit(List<DocumentoDb> documentoDb);

    static PaginaResponse<DocumentoEdit> pageToPaginaResponseDocumentoEdit(
            Page<DocumentoDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.documentoDbToDocumentoEdit(page.getContent()),
                filtros,
                ordenaciones);
    }
}
