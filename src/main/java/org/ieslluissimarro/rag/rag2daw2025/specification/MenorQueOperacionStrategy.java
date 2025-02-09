package org.ieslluissimarro.rag.rag2daw2025.specification;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.TipoOperacionBusqueda;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class MenorQueOperacionStrategy implements OperacionBusquedaStrategy {
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Predicate crearPredicado(
        Root<?> root,
        CriteriaBuilder criteriaBuilder,
        FiltroBusqueda filtro
    ) {
        return criteriaBuilder.lessThan(
            root.get(filtro.getAtributo()),
            (Comparable) filtro.getValor()
        );
    }

    @Override
    public boolean soportaOperacion(TipoOperacionBusqueda operacion) {
        return operacion == TipoOperacionBusqueda.MENOR_QUE;
    }
}
