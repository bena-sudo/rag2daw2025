package org.ieslluissimarro.rag.rag2daw2025.model.db;
import java.time.LocalDateTime;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

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
    @Column(name = "\"user\"")
    private String usuario;
    @Generated(GenerationTime.INSERT)
    @Column(name = "fecha", insertable = false,  updatable = false)
    private LocalDateTime fecha;
    @Column(name = "contexto")
    @NotNull
    private Integer contexto;

}
