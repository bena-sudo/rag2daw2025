package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.util.HashSet;
import java.util.Set;

import org.ieslluissimarro.rag.rag2daw2025.model.db.EtiquetaDB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoList {

   private Long id;
   private Integer idDocRag;
   // private String dni;
   private String nombreFichero;
   private String estado;
   private Set<EtiquetaDB> etiquetas = new HashSet<>();
}
