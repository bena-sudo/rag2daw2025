package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentoDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoEditDocu;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoNew;
import org.ieslluissimarro.rag.rag2daw2025.utils.MultipartUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@SuppressWarnings("unused")
@Mapper(imports = {MultipartUtils.class})
public interface DocumentoMapperDocu {
    
    DocumentoMapperDocu INSTANCE = Mappers.getMapper(DocumentoMapperDocu.class);

    DocumentoEditDocu documentoDBToDocumentoEdit(DocumentoDB documentoDb);

    DocumentoInfo documentoDBToDocumentoInfo(DocumentoDB documentoDb);

    DocumentoList documentoDBToDocumentoList(DocumentoDB documentoDb);

    DocumentoNew documentoDBToDocumentoNew(DocumentoDB documentoDb);

    /**
     * Mapeo de DocumentoNew a DocumentoDB, convirtiendo el archivo en Base64, su extensión y tipo MIME
     */
    @Mapping(target = "base64Documento", expression = "java(MultipartUtils.multipartToString(documentoNew.getMultipart()))")
    @Mapping(target = "extensionDocumento", expression = "java(MultipartUtils.getExtensionMultipartfile(documentoNew.getMultipart()))")
    @Mapping(target = "contentTypeDocumento", expression = "java(documentoNew.getMultipart().getContentType())")
    DocumentoDB documentoNewToDocumentoDB(DocumentoNew documentoNew);

    List<DocumentoList> documentoDBToDocumentoList(List<DocumentoDB> documentoDb);

    /**
     * Actualización de DocumentoDB a partir de DocumentoEdit, incluyendo la conversión de MultipartFile
     */
    @Mapping(target = "base64Documento", expression = "java(documentoEdit.getMultipart() != null ? MultipartUtils.multipartToString(documentoEdit.getMultipart()) : documentoDb.getBase64Documento())")
    @Mapping(target = "extensionDocumento", expression = "java(documentoEdit.getMultipart() != null ? MultipartUtils.getExtensionMultipartfile(documentoEdit.getMultipart()) : documentoDb.getExtensionDocumento())")
    @Mapping(target = "contentTypeDocumento", expression = "java(documentoEdit.getMultipart() != null ? documentoEdit.getMultipart().getContentType() : documentoDb.getContentTypeDocumento())")
    void updateDocumentoDBFromDocumentoEdit(DocumentoEditDocu documentoEdit, @MappingTarget DocumentoDB documentoDb);

    /**
     * Conversión de una página de DocumentoDB en una respuesta paginada
     */
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
