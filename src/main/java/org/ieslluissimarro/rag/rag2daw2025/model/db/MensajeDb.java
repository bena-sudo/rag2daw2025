package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.time.LocalDateTime;

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
@Table(name = "mensajes")
public class MensajeDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "acreditacion_id")
    private EstadoAcreditacionDb acreditacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioDb usuario;

    private String contenido;
    @Column(name = "fecha")
    private LocalDateTime fecha = LocalDateTime.now();
}
