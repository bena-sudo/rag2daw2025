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
    int number;
    int size;
    long totalElements;
    int totalPages;

    List<T> content;
    Sort sort;
}