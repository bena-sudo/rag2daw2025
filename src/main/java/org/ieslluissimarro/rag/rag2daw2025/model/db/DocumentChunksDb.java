package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.time.LocalDate;

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
    private Long idDocumentChunk;

    private LocalDate fechaCreado;




}
