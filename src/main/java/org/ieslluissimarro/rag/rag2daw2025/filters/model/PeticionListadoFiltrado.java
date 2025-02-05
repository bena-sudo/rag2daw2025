package org.ieslluissimarro.rag.rag2daw2025.filters.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PeticionListadoFiltrado {
    private List<FiltroBusqueda> listaFiltros;
    private Integer page;
    private Integer size;
    private List<String> sort;
}
