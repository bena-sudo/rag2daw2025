package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documentos")
public class DocumentDB {
  
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "id_doc_rag")
   private Integer idDocRag;

   // @Column(name = "dni", nullable = false)
   // private String dni;


   @Column(name = "nombre_fichero", nullable = false)
   private String nombreFichero;


   @Column(name = "comentario")
   private String comentario;


   @Column(name = "base64_documento")
   private String base64Documento;


   @Column(name = "extension_documento")
   private String extensionDocumento;


   @Column(name = "content_type_documento")
   private String contentTypeDocumento;


   @Column(name = "estado_documento", nullable = false)
   private String estadoDocumento = "pendiente";


   @Column(name = "fecha_creacion", nullable = false)
   private LocalDateTime fechaCreacion;

   @Column(name = "fecha_revision")
   private LocalDateTime fechaRevision;


}

