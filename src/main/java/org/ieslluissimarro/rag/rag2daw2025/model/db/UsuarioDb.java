package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

    private LocalDate fechaNacimiento;

    @Column(nullable = false, length = 20)
    private String estado;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<RolDb> roles;

    @ManyToMany
    @JoinTable(
        name = "usuarios_permisos",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    private Set<PermisoDb> permisos;


    public UsuarioDb(@NotNull String nombre, @NotNull String nickname, @NotNull String email,
    @NotNull String password, @NotNull String telefono, @NotNull LocalDate fechaNacimiento) {
    this.nombre = nombre;
    this.nickname = nickname;
    this.email = email;
    this.password = password;
    this.telefono = telefono;
    this.fechaNacimiento = fechaNacimiento;
}
}