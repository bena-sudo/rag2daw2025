package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.helper.FiltroBusquedaQualitat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PeticionListadoFiltrado {

    private List<FiltroBusquedaQualitat> listaFiltros;

    private Integer page;

    private Integer size;

    private List<String> sort;
}
