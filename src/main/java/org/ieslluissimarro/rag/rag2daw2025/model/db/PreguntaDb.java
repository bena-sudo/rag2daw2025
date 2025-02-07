package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pregunta")
    private Long idPregunta;
    /*@Column(name = "id_chat")
    private Long idChat;*/
    @Column(name = "\"user\"")
    private String usuario;
    @Column(name = "texto_pregunta")
    private String textoPregunta;
    @Column(name = "texto_respuesta")
    private String textoRespuesta;
    @Column(name = "feedback")
    private String feedback;
    @Column(name = "valorado")
    private Boolean valorado;

    @ManyToOne
    @JoinColumn(name = "id_chat", nullable = false)
    private ChatDb idChat;

    /* 
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "pregunta_documentchunks", joinColumns =
     @JoinColumn(name="id_pregunta"), inverseJoinColumns = @JoinColumn(name="id_documentchunk"))
    private Set<DocumentChunksDb> documentchunksImportantes = new HashSet<>();
*/

}
