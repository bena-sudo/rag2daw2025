package org.ieslluissimarro.rag.rag2daw2025.model.enums;

public enum TipoOperacionBusqueda {
    IGUAL("="),
    CONTIENE("LIKE"),
    MAYOR_QUE(">"),
    MENOR_QUE("<");

    private final String simbolo;

    TipoOperacionBusqueda(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public static TipoOperacionBusqueda deSimbolo(String simbolo) {
        for (TipoOperacionBusqueda operacion : values()) {
            if (operacion.simbolo.equals(simbolo)) {
                return operacion;
            }
        }
        throw new IllegalArgumentException("Simbolo de operación no válido: " + simbolo);
    }
}
