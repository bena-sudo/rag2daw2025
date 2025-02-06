package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.ieslluissimarro.rag.rag2daw2025.model.enums.EstadoDocumento;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEdit {

    private Long id;

    private Integer idDocRag;

    @NotNull(message = "El id del usuario no puede estar vacío")
    private Long idUsuario;

    private String comentario;

    @Enumerated(EnumType.STRING)
    private EstadoDocumento estadoDocumento;

    @NotNull
    @Size(max = 255, message = "El nombre debe tener un tamaño máximo de 255 caracteres")
    @Column(name = "nombre_fichero", nullable = false, length = 255)
    private String nombreFichero;

    // Campo para el archivo (no se persiste directamente en la base de datos. Debe pasarse a base64)
    private MultipartFile multipart;

    @Size(max = 5, message = "La extensión del documento debe tener como máximo 5 caracteres")
    @Column(name = "extension_documento")
    private String extensionDocumento;

    @Size(max = 50, message = "El tipo de documento debe tener como máximo 50 caracteres")
    @Column(name = "content_type_documento")
    private String contentTypeDocumento;

}
