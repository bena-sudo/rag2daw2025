package org.ieslluissimarro.rag.rag2daw2025.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "auditoria_eventos")
public class AuditoriaEventoDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "tipo_evento", nullable = false)
    private String tipoEvento;

    @Column(name = "tabla_afectada")
    private String tablaAfectada;

    @Column(name = "dato_anterior")
    private String datoAnterior;

    @Column(name = "dato_nuevo")
    private String datoNuevo;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;
}