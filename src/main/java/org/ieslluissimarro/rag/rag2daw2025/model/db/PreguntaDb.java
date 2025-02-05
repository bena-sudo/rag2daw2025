package org.ieslluissimarro.rag.rag2daw2025.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="preguntas")
public class PreguntaDb {

    @Id
    @Column(name = "id_pregunta")
    private Long idPregunta;
    @Column(name = "id_chat")
    private Long idChat;
    @Column(name = "user")
    private String usuario;
    @Column(name = "texto_pregunta")
    private String textoPregunta;
    @Column(name = "texto_respuesta")
    private String textoRespuesta;
    @Column(name = "valoracion")
    private String valoracion;
    @Column(name = "valorado")
    private Boolean valorado;
    }
