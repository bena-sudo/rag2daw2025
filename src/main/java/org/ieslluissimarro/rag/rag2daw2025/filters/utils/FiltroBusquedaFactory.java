package org.ieslluissimarro.rag.rag2daw2025.filters.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.TipoOperacionBusqueda;
import org.springframework.stereotype.Component;
@Component
public class FiltroBusquedaFactory {

    private final static CharSequence separador = ":";

    /**
     * Convierte un array de cadenas en una lista de FiltroBusqueda.
     * 
     * @param filtros Array de filtros en formato "atributo:Operador:valor"
     * @return Lista de FiltroBusqueda
          * @throws FiltroException 
          */
          public List<FiltroBusqueda> crearListaFiltrosBusqueda(List<String> filtros) throws FiltroException {
            if (filtros == null || filtros.isEmpty()) {
                return Collections.emptyList();
            }
    
            try {
                // Usamos el método stream() de List para procesar cada filtro
                return filtros.stream()
                        .map(FiltroBusquedaFactory::createFiltro)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                throw new FiltroException(
                    "BAD_FILTER",
                    "Error: Filtro incorrecto",
                    e.getMessage()
                );
            }
        
    }

    private static FiltroBusqueda createFiltro(String filtro) {
        if (filtro == null || !filtro.contains(separador)) {
            throw new IllegalArgumentException("El filtro proporcionado no tiene el formato esperado" +
                    " (ATRIBUTO" + separador.toString() + "OPERACION" + separador.toString() + "VALOR).");
        }

        String[] partes = filtro.split(separador.toString(), 3);
        if (partes.length < 3) {
            throw new IllegalArgumentException("El filtro proporcionado no tiene el formato esperado" +
                    " (ATRIBUTO" + separador.toString() + "OPERACION" + separador.toString() + "VALOR).");
        }

        String atributo = partes[0].trim();
        String operacionTexto = partes[1].trim();
        // Concatenar las partes restantes para el valor si hay más de 3 partes
        String valor = Arrays.stream(partes, 2, partes.length)
                .reduce((a, b) -> a + separador.toString() + b)
                .orElse("").trim();

        TipoOperacionBusqueda operacion;
        try {
            operacion = TipoOperacionBusqueda.valueOf(operacionTexto.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Operación no válida: " + operacionTexto);
        }

        return new FiltroBusqueda(atributo, operacion, valor);
    }
}