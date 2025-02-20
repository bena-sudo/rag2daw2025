package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.ieslluissimarro.rag.rag2daw2025.model.enums.EstadoDocumento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documentos")
public class DocumentoDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "Identificador único del documento")
    private Long id;

    @Column(name = "id_doc_rag")
    @Schema(example = "100", description = "Identificador del documento en el sistema RAG (opcional)")
    private Integer idDocRag;

    @NotNull(message = "El id del usuario no puede estar vacío")
    @Column(name = "id_usuario", nullable = false)
    @Schema(example = "25", description = "Identificador del usuario propietario del documento")
    private Long idUsuario;

    @Size(max = 255, message = "El nombre del fichero debe tener como máximo 255 caracteres")
    @Column(name = "nombre_fichero", nullable = false)
    @Schema(example = "reporte_financiero.pdf", description = "Nombre del fichero del documento")
    private String nombreFichero;

    @Column(name = "comentario")
    @Schema(example = "Este es un documento importante", description = "Comentario o descripción adicional sobre el documento")
    private String comentario;

    @NotNull(message = "El documento no puede estar vacio")
    @Column(name = "base64_documento", nullable = false, columnDefinition = "TEXT")
    @Schema(example = "base64_encoded_string_here", description = "Contenido del documento en formato Base64")
    private String base64Documento;

    @Size(max = 5, message = "La extensión del documento debe tener como máximo 5 caracteres")
    @Column(name = "extension_documento")
    @Schema(example = "pdf", description = "Extensión del archivo del documento")
    private String extensionDocumento;

    @Size(max = 100, message = "El tipo de contenido debe tener como máximo 100 caracteres")
    @Column(name = "content_type_documento")
    @Schema(example = "application/pdf", description = "Tipo de contenido del documento (MIME type)")
    private String contentTypeDocumento;

    @Size(max = 50, message = "El tipo de documento debe tener como máximo 50 caracteres")
    @Column(name = "tipo_documento")
    @Schema(example = "REPORTE", description = "Tipo de documento")
    private String tipoDocumento;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "estado_documento")
    @Schema(example = "EN_REVISION", description = "Estado del documento")
    private EstadoDocumento estado;

    @Column(name = "fecha_creacion", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Schema(example = "2023-01-01T12:00:00", description = "Fecha y hora de creación del documento")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_revision")
    @Schema(example = "2023-01-05T12:00:00", description = "Fecha y hora de la última revisión del documento")
    private LocalDateTime fechaRevision;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "documentos_etiquetas", joinColumns = @JoinColumn(name = "id_documento"), inverseJoinColumns = @JoinColumn(name = "id_etiqueta"))
    private Set<EtiquetaDB> etiquetas = new HashSet<>();
}
