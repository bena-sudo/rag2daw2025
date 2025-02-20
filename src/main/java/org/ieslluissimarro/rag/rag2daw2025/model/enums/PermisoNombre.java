package org.ieslluissimarro.rag.rag2daw2025.model.enums;

public enum PermisoNombre {
    //CREAR_USUARIO("Permite crear nuevos usuarios"),
    EDITAR_USUARIO("Permite editar la información de los usuarios"),
    ELIMINAR_USUARIO("Permite eliminar usuarios"),
    VER_USUARIOS("Permite ver la lista de usuarios"),
    VER_USUARIO("Permite ver la información de un usuario"),
    ASIGNAR_ROLES("Permite asignar roles a los usuarios"),
    //CREAR_ROL("Permite crear nuevos roles"),
    //EDITAR_ROL("Permite modificar los roles existentes"),
    //ELIMINAR_ROL("Permite eliminar roles"),
    GESTIONAR_PERMISOS("Permite gestionar los permisos de los roles"),
    VER_AUDITORIA("Permite ver los registros de auditoría"),
    DESBLOQUEAR_CUENTAS("Permite desbloquear cuentas de usuario"),
    VER_CUENTAS_BLOQUEADAS("Permite ver las cuentas de usuario bloqueadas"),
    VER_PERMISOS_POR_ROL("Permite ver los permisos de los roles"),
    VER_PERMISOS("Permite ver los permisos existentes"),
    ASIGNAR_PERMISOS_A_ROL("Permite asignar permisos a los roles"),
    VER_USUARIOS_POR_ROL("Permite ver los usuarios que tienen un rol concreto"),
    //GESTIONAR_SESIONES("Permite gestionar sesiones activas");

    VER_SESIONES_ACTIVAS("Permite listar las sesiones activas"),

    EDITAR_DOCUMENTO("Permite editar documentos"), // ADMIN, ASESOR
    CREAR_DOCUMENTO("Permite crear documentos"), // ADMIN, ASESOR,USUARI
    ELIMINAR_DOCUMENTO("Permite eliminar documentos"), // ADMIN, ASESOR,USUARI
    VER_DOCUMENTOS("Permite ver documentos"), // ADMIN, ASESOR,USUARI

    EDITAR_CHUNKS("Permite editar chunks"),
    ELIMINAR_CHUNKS("Permite eliminar chunks"),
    VER_CHUNKS("Permite ver chunks"),
    VER_ESTADISTICAS("Permite ver estadísticas"),//SUPERVISOR, ADMIN

    VER_ETIQUETAS("Permite ver etiquetas"), //USER, ADMIN
    EDITAR_ETIQUETAS("Permite editar etiquetas"),
    ELIMINAR_ETIQUETAS("Permite eliminar etiquetas"),
    CREAR_ETIQUETAS("Permite crear etiquetas"),

    CREAR_CHAT("Permite crear un chat"),//SUPERVISOR
    VER_CHATS("Permite ver todos los chats"),
    VER_PREGUNTAS("Permite ver todas las pregunttas"),
    ELIMINAR_CHAT("Permite eliminar un chat"),
    MODIFICAR_CHAT("Permite modificar un chat"),
    VER_LISTA_CONTEXTOS("Permite ver las listas de contextos"),
    CREAR_PREGUNTA("Permite crear preguntas"),
    MODIFICAR_PREGUNTA("Permite modificar preguntas"),

    CREAR_ACREDITACIONES("Permite crear acreditaciones"), //USUARIO ACREDITADOR
    CREAR_MENSAJE("Permite crear mensajes"),
    VER_ACREDITACIONES("Permite ver acreditaciones");    








    

    private final String descripcion;

    PermisoNombre(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}