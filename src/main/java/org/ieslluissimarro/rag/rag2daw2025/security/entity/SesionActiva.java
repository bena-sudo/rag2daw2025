package org.ieslluissimarro.rag.rag2daw2025.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "sesiones_activas")
public class SesionActiva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioDb usuario;

    @Column(name = "token_sesion", unique = true, nullable = false, length = 500)
    private String tokenSesion;

    @Column(name = "fecha_inicio", nullable = false, updatable = false)
    private LocalDateTime fechaInicio = LocalDateTime.now();

    @Column(name = "fecha_expiracion")
    private LocalDateTime fechaExpiracion;

    public SesionActiva(UsuarioDb usuario, String tokenSesion) {
        this.usuario = usuario;
        this.tokenSesion = tokenSesion;
        this.fechaInicio = LocalDateTime.now();
        this.fechaExpiracion = LocalDateTime.now().plusMinutes(10); // Expira en 30 minutos
    }
}

