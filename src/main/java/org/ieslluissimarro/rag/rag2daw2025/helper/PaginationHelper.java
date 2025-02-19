package org.ieslluissimarro.rag.rag2daw2025.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class PaginationHelper {

    private PaginationHelper() {
        // Constructor privado para prevenir instanciación
    }

    public static Pageable createPageable(int page, int size, String[] sort) {

        // Crear sorts (ordenacion de los datos)
        List<Order> criteriosOrdenacion = new ArrayList<>();

        // El primer criterio de ordenacion siempre debera de contener el orden (asc, desc)
        if (sort[0].contains(",")) {
            // Tenemos un vector de ordenaciones en 'sort' y debemos leerlos
            for (String criterioOrdenacion : sort) {
                String[] orden = criterioOrdenacion.split(",");
                if (orden.length > 1) {
                    criteriosOrdenacion.add(new Order(Direction.fromString(orden[1]), orden[0]));
                } else {
                    // por defecto asc si no se dice nada
                    criteriosOrdenacion.add(new Order(Direction.fromString("asc"), orden[0]));
                }
            }
        } else {
            // Solo hay un criterio de ordenacion
            criteriosOrdenacion.add(new Order(Direction.fromString(sort[1]), sort[0]));
        }
        Sort sorts = Sort.by(criteriosOrdenacion);

        return PageRequest.of(page, size, sorts);
    }
}
