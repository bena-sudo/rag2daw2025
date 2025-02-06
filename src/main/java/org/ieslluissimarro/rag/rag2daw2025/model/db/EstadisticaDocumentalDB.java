package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "estadisticas_documental")
public class EstadisticaDocumentalDB implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_documento")
    private Long idDocumento;

    @Column(name = "id_chunk")
    private Long idChunk;

    @Column(name = "tiempo_revision")
    private Integer tiempoRevision;

    private LocalDateTime fecha;

    private Long usuario;

    @Size(min=3 , max = 20)
    @Column(name = "estado_final")
    private String estadoFinal;
}
