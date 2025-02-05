package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="feedbacks")
public class DocumentChunksDb {

    @Id
    @Column(name = "id_documentchunk")
    private Long idDocumentChunk;
    @Column(name = "id_doc_source")
    private Long idDocSource;
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreado;




}
