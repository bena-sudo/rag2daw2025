package org.ieslluissimarro.rag.rag2daw2025.helper;

import org.ieslluissimarro.rag.rag2daw2025.model.enums.TipoOperacionBusqueda;
import org.springframework.lang.NonNull;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FiltroBusqueda {

    @Size(min = 1, message = "Debe especificar un atributo")
    private String atributo;

    @Size(min = 1, message = "Debe especificar una operacion")
    private TipoOperacionBusqueda operacion;

    @NonNull
    private String valor;
}
