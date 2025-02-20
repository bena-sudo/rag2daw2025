package org.ieslluissimarro.rag.rag2daw2025.security.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;

@Data
@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = UsuarioDb.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "usuario_id")
    private UsuarioDb usuario;

    private LocalDateTime expiryDate;

}
