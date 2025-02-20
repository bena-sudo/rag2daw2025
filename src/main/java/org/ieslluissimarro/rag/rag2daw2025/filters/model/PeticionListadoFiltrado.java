package org.ieslluissimarro.rag.rag2daw2025.filters.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PeticionListadoFiltrado implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private List<FiltroBusqueda> listaFiltros;
    private Integer page;
    private Integer size;
    private List<String> sort;
}
