package org.ieslluissimarro.rag.rag2daw2025.model.enums;

public enum RolNombre {
    USUARIO("Accede a la plataforma y consulta su historial de acreditaciones"),
    ASESOR("Gestiona documentación de candidatos y asesora sobre competencias"),
    SUPERVISOR("Supervisa chunks del RAG y revisa respuestas en chats"),
    ACREDITADOR("Aprueba o rechaza acreditaciones en base a criterios establecidos"),
    EVALUADOR("Evalúa competencias profesionales según normativas"),
    PROFESOR("Puede acceder a materiales y revisar evaluaciones de alumnos"),
    JEFEDPTO("Supervisa la gestión académica y formación dentro de su departamento"),
    JEFEESTUDIOS("Coordina la acreditación y supervisa evaluadores y asesores"),
    ADMINISTRADOR("Gestiona usuarios, roles y configuración global de la aplicación");

    private final String descripcion;

    RolNombre(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return descripcion;
    }
}
