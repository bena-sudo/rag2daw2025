package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "estado_acreditacion")
public class EstadoAcreditacionDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String estado;
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fecha_actualizacion = LocalDateTime.now();

    @Column(name = "usuario_id")
    private Long usuario_id;
    
    private Long asesor_id;

    @OneToOne
    @JoinColumn(name = "modulo_id")
    private ModuloDb modulo;
}