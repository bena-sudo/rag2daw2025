package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentoEdit {
    private Long id;
    private int idDocRag;
    private Long id_usuario;
    private String nombreFichero;
    private String comentario;
    private String tipo_documento;
    private String base64Documento;
    private String extensionDocumento;
    private String contentTypeDocumento;
    private String estadoDocumento;
    private java.sql.Timestamp fechaCreacion;
    private java.sql.Timestamp fechaRevision;
}
