package org.ieslluissimarro.rag.rag2daw2025.model.db;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="chats")
public class ChatDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat")
    private Long idChat;
    @Column(name = "user")
    private String usuario;
    @Column(name = "fecha")
    private LocalDate fecha;

    @OneToMany
    @JoinColumn(name="preguntas", referencedColumnName = "id_pregunta")
    private List<PreguntaDb> preguntas;

}
