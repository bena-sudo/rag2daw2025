package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.List;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
class UsuarioEdit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String nickname;
    private String email;
    private String telefono;
    private Date fechaNacimiento;
    private String estado;
    private Date fechaCreacion;
    
    @ManyToMany
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private List<RolDTO> roles;
    
    @ManyToMany
    @JoinTable(
        name = "usuarios_permisos",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    private List<PermisoDTO> permisos;
}


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categorias")
class CategoriaDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    
    @OneToMany(mappedBy = "categoria")
    private List<PermisoDTO> permisos;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bloqueo_cuentas")
class BloqueoCuentaDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioDTO usuario;
    
    private Integer intentosFallidos;
    private Boolean bloqueado;
    private Date fechaBloqueo;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sesiones_activas")
class SesionActivaDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioDTO usuario;
    
    private String tokenSesion;
    private String ipOrigen;
    private String dispositivo;
    private Date fechaInicio;
    private Date fechaExpiracion;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "intentos_login")
class IntentoLoginDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioDTO usuario;
    
    private String ipOrigen;
    private Boolean exito;
    private Date fecha;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auditoria_eventos")
class AuditoriaEventoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioDTO usuario;
    
    private String tipoEvento;
    private String tablaAfectada;
    private String datoAnterior;
    private String datoNuevo;
    private String descripcion;
    private Date fecha;
}
