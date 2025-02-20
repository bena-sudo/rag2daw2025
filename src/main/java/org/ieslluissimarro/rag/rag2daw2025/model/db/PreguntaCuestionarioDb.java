package org.ieslluissimarro.rag.rag2daw2025.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "preguntas_cuestionarios")
public class PreguntaCuestionarioDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "texto", nullable = false)
    private String texto;
    @ManyToOne
    @JoinColumn(name = "tipo_id", nullable = false)
    private TipoPreguntaDb tipo;
    @ManyToOne
    @JoinColumn(name = "cuestionario_id", nullable = false)
    private CuestionarioDb cuestionario;
    @ManyToOne
    @JoinColumn(name = "siguiente_si")
    private PreguntaCuestionarioDb siguienteSi;
    @ManyToOne
    @JoinColumn(name = "siguiente_no")
    private PreguntaCuestionarioDb siguienteNo;
    @Column(name = "final_si", nullable = false)
    private boolean finalSi;
    @Column(name = "final_no", nullable = false)
    private boolean finalNo;
    @Column(name = "explicacion_si")
    private String explicacionSi;
    @Column(name = "explicacion_no")
    private String explicacionNo;
    @Column(name = "orden", nullable = false)
    private int orden;
}
