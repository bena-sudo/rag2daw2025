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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "bloqueo_cuentas")
public class BloqueoCuentaDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", unique = true, nullable = false)
    private Long usuarioId;

    @Column(name = "intentos_fallidos", nullable = false)
    private int intentosFallidos;

    @Column(name = "bloqueado", nullable = false)
    private boolean bloqueado;

    @Column(name = "fecha_bloqueo")
    private LocalDateTime fechaBloqueo;


    public BloqueoCuentaDb(Long usuarioId, int intentosFallidos, boolean bloqueado, LocalDateTime fechaBloqueo) {
        this.usuarioId = usuarioId;
        this.intentosFallidos = intentosFallidos;
        this.bloqueado = bloqueado;
        this.fechaBloqueo = fechaBloqueo;
    }

}
