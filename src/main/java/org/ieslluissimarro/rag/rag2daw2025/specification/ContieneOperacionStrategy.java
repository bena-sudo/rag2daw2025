package org.ieslluissimarro.rag.rag2daw2025.specification;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.TipoOperacionBusqueda;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ContieneOperacionStrategy implements OperacionBusquedaStrategy {
    @Override
    public Predicate crearPredicado(
        Root<?> root,
        CriteriaBuilder criteriaBuilder,
        FiltroBusqueda filtro
    ) {
        return criteriaBuilder.like(
            root.get(filtro.getAtributo()), "%" + filtro.getValor() + "%");
    }

    @Override
    public boolean soportaOperacion(TipoOperacionBusqueda operacion) {
        return operacion == TipoOperacionBusqueda.CONTIENE;
    }
}
