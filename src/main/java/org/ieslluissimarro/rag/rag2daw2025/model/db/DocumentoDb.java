package org.ieslluissimarro.rag.rag2daw2025.model.db;

import org.ieslluissimarro.rag.rag2daw2025.model.enums.EstadoDocumento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties({"usuario"})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "documentos")
public class DocumentoDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "id_doc_rag")
    private int idDocRag;

    @Column(name = "id_usuario")
    private Long id_usuario;

    @Column(name = "nombre_fichero")
    private String nombreFichero;

    private String comentario;

    @Column(name = "tipo_documento")
    private String tipo_documento;

    @Column(name = "base64_documento", columnDefinition = "TEXT")
    private String base64Documento;


    @Column(name = "extension_documento")
    private String extensionDocumento;

    @Column(name = "content_type_documento")
    private String contentTypeDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_documento")
    private EstadoDocumento estadoDocumento;

    @Column(name = "fecha_creacion")
    private java.sql.Timestamp fechaCreacion;

    @Column(name = "fecha_revision")
    private java.sql.Timestamp fechaRevision;
}