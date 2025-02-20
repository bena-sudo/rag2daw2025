package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.util.HashSet;
import java.util.Set;

import org.ieslluissimarro.rag.rag2daw2025.model.db.EtiquetaDB;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.EstadoDocumentoDocu;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoEditDocu {

   private Long id;

   private Integer idDocRag;

   @NotNull(message = "El id del usuario no puede estar vacío")
   private Long idUsuario;

   private String comentario;

   private EstadoDocumentoDocu estado;

   private String nombreFichero;

   // Campo para el archivo (no se persiste directamente en la base de datos. Debe pasarse a base64)
   private MultipartFile multipart;

   private Set<EtiquetaDB> etiquetas = new HashSet<>();

}
