package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.time.LocalDateTime;

import org.ieslluissimarro.rag.rag2daw2025.model.enums.EstadoDocumento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
   private Long id;

   @Column(name = "id_doc_rag")
   private Integer idDocRag;

   // @Column(name = "dni", nullable = false)
   // private String dni;

   @NotNull(message = "El id del usuario no puede estar vacío")
   @Column(name = "id_usuario", nullable = false)
   private Long idUsuario;

   @Size(max = 255, message = "El nombre del fichero debe tener como máximo 255 caracteres")
   @Column(name = "nombre_fichero", nullable = false)
   private String nombreFichero;

   @Column(name = "comentario")
   private String comentario;

   @NotNull(message = "El documento no puede estar vacio")
   @Column(name = "base64_documento", nullable = false, columnDefinition = "TEXT")
   private String base64Documento;

   @Size(max = 5, message = "La extensión del documento debe tener como máximo 5 caracteres")
   @Column(name = "extension_documento")
   private String extensionDocumento;

   @Size(max = 100, message = "El tipo de contenido debe tener como máximo 100 caracteres")
   @Column(name = "content_type_documento")
   private String contentTypeDocumento;

   @Size(max = 50, message = "El tipo de documento debe tener como máximo 50 caracteres")
   private String tipoDocumento;

   @Enumerated(EnumType.STRING)
   @Column(length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'pendiente'")
   private EstadoDocumento estado;

   @Column(name = "fecha_creacion", nullable = false)
   private LocalDateTime fechaCreacion;

   @Column(name = "fecha_revision")
   private LocalDateTime fechaRevision;

}
