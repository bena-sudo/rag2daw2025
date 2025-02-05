package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentInfo {

    private Long id;
    private String dni;
    private String nombreFichero;
    private String comentario;
    private String base64Documento;
    private String extensionDocumento;
    private String contentTypeDocumento;
    private String estadoDocumento;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaRevision;
    
}
