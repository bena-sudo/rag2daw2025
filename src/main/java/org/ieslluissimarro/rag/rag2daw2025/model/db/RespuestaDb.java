package org.ieslluissimarro.rag.rag2daw2025.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "respuestas")
public class RespuestaDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String respuesta;

    @OneToOne
    @JoinColumn(name = "pregunta_id")
    private PreguntaCuestionarioDb pregunta;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioDb usuario;
}