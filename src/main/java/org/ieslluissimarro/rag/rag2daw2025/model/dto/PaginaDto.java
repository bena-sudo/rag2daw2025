package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.util.List;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginaDto<T> {

    // Numero de página solicitada
    int number;

    // Tamaño de la página
    int size;

    // Total de elementos devueltos por la consulta sin paginación
    long totalElements;

    // Total páginas teniendo en cuenta el tamaño de cada página
    int totalPages;

    // Lista de elementos
    List<T> content;

    // Ordenación de la consulta
    Sort sort;
}
