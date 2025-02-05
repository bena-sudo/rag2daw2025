package org.ieslluissimarro.rag.rag2daw2025.model.db;
import java.time.LocalDate;

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
@Table(name="chats")
public class ChatDb {

    @Id
    @Column(name = "id_chat")
    private Long idChat;
    @Column(name = "user")
    private String usuario;
    @Column(name = "fecha")
    private LocalDate fecha;


}
