package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.time.LocalDateTime;
import java.util.Set;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuarios")
public class UsuarioDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(nullable = false, unique = true, length = 255)
    private String nickname;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 15)
    private String telefono;

    // @Column(name = "fecha_nacimiento", nullable = false)
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    // private LocalDate fechaNacimiento;

    @Column(nullable = false, length = 20)
    private String estado = "pendiente";

    @Column(name = "fecha_creacion", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaCreacion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<RolDb> roles;


    public UsuarioDb(@NotNull String nombre, @NotNull String nickname, @NotNull String email,
                     @NotNull String password, @NotNull String telefono) {
        this.nombre = nombre;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.fechaCreacion = LocalDateTime.now();
    }
}
