package org.ieslluissimarro.rag.rag2daw2025.model.enums;

public enum PermisoNombre {
    CREAR_USUARIO("Permite crear nuevos usuarios"),
    EDITAR_USUARIO("Permite editar la información de los usuarios"),
    ELIMINAR_USUARIO("Permite eliminar usuarios"),
    VER_USUARIOS("Permite ver la lista de usuarios"),
    ASIGNAR_ROLES("Permite asignar roles a los usuarios"),
    CREAR_ROL("Permite crear nuevos roles"),
    EDITAR_ROL("Permite modificar los roles existentes"),
    ELIMINAR_ROL("Permite eliminar roles"),
    GESTIONAR_PERMISOS("Permite gestionar los permisos de los roles"),
    VER_AUDITORIA("Permite ver los registros de auditoría"),
    GESTIONAR_SESIONES("Permite gestionar sesiones activas");

    private final String descripcion;

    PermisoNombre(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}