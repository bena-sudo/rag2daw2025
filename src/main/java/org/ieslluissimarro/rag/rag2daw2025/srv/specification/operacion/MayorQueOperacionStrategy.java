package org.ieslluissimarro.rag.rag2daw2025.srv.specification.operacion;

import org.ieslluissimarro.rag.rag2daw2025.helper.FiltroBusquedaQualitat;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.TipoOperacionBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.srv.specification.OperacionBusquedaStrategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class MayorQueOperacionStrategy implements OperacionBusquedaStrategy {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Predicate crearPredicado(
            Root<?> root,
            CriteriaBuilder criteriaBuilder,
            FiltroBusquedaQualitat filtro) {
        return criteriaBuilder.greaterThan(
                root.get(filtro.getAtributo()),
                (Comparable) filtro.getValor());
    }

    @Override
    public boolean soportaOperacion(TipoOperacionBusqueda operacion) {
        return operacion == TipoOperacionBusqueda.MAYOR_QUE;
    }
}
