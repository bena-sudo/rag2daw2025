package org.ieslluissimarro.rag.rag2daw2025.filters.specification.operacion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.ieslluissimarro.rag.rag2daw2025.filters.model.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.TipoOperacionBusqueda;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class IgualOperacionStrategy implements OperacionBusquedaStrategy {
    @Override
   public Predicate crearPredicado(
        Root<?> root, 
        CriteriaBuilder criteriaBuilder, 
        FiltroBusqueda filtro
    ) {
        if (root.get(filtro.getAtributo()).getJavaType().equals(java.time.LocalDateTime.class)) {
            LocalDate fecha;
            fecha = LocalDate.parse((CharSequence) filtro.getValor(), DateTimeFormatter.ISO_DATE);

            // Truncar el LocalDateTime a solo la fecha
            Expression<LocalDate> fechaConvertida = criteriaBuilder.function(
                "DATE", LocalDate.class, root.get(filtro.getAtributo())
            );

            // Comparar solo la fecha
            return criteriaBuilder.equal(fechaConvertida, fecha);
        }

        return criteriaBuilder.equal(
            root.get(filtro.getAtributo()), 
            filtro.getValor()
        );
    }

    @Override
    public boolean soportaOperacion(TipoOperacionBusqueda operacion) {
        return operacion == TipoOperacionBusqueda.IGUAL;
    }
}

