package org.ieslluissimarro.rag.rag2daw2025.helper;

import java.util.ArrayList;
import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.enums.TipoOperacionBusqueda;

public class FiltroBusquedaHelper {

    public static List<FiltroBusquedaQualitat> createFiltroBusqueda(String[] filtros) {
        List<FiltroBusquedaQualitat> listaFiltros = new ArrayList<>();

        if (filtros == null || filtros.length == 0) {
            return listaFiltros;
        }

        for (String filtro : filtros) {
            int posOperador = -1;
            String operador = null;

            if ((posOperador = filtro.indexOf(">")) != -1) {
                operador = ">";
            } else if ((posOperador = filtro.indexOf("<")) != -1) {
                operador = "<";
            } else if ((posOperador = filtro.indexOf(":")) != -1) {
                operador = ":";
            }

            if (operador == null) {
                throw new IllegalArgumentException("El filtrono contiene un operador valido: " + filtro);
            }

            // Extraer el atributo (todo antes del operador) y el valor (todo después del operador)
            String atributo = filtro.substring(0, posOperador).trim();
            String valor = filtro.substring(posOperador + 1).trim();

            if (atributo.isEmpty() || valor.isEmpty()) {
                throw new IllegalArgumentException("El filtro debe tener un atributo y un valor: " + filtro);
            }

            // Crear un nuevo FiltroBusqueda y añadirlo a la lista
            listaFiltros.add(new FiltroBusquedaQualitat(atributo, TipoOperacionBusqueda.deSimbolo(operador), valor));
        }
        return listaFiltros;
    }
}
