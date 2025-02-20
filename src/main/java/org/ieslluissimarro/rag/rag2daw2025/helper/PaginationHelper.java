package org.ieslluissimarro.rag.rag2daw2025.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class PaginationHelper {
    
    private PaginationHelper(){

    }
/*
    public static Pageable createPageable(int page, int size, String[] sort) {
        List<Order> cristeriosOrdenacion = new ArrayList<Order>();
        
        if (sort != null && sort.length > 0) {
            if (sort.length == 1) {
                // Solo un criterio de ordenación (por ejemplo, "field")
                cristeriosOrdenacion.add(new Order(Direction.ASC, sort[0]));
            } else if (sort[0].contains(",")) {
                // Varias ordenaciones separadas por comas (por ejemplo, "field1,desc,field2,asc")
                for (String cristerioOrdenacion : sort) {
                    String[] order = cristerioOrdenacion.split(",");
                    if (order.length > 1) {
                        cristeriosOrdenacion.add(new Order(Direction.fromString(order[1]), order[0]));
                    } else {
                        cristeriosOrdenacion.add(new Order(Direction.ASC, order[0]));
                    }
                }
            } else {
                // Criterio de ordenación con dirección explícita (por ejemplo, "field,asc")
                cristeriosOrdenacion.add(new Order(Direction.fromString(sort[1]), sort[0]));
            }
        }
        
        Sort sorts = Sort.by(cristeriosOrdenacion);
        return PageRequest.of(page, size, sorts);
    }

    */


    public static Pageable createPageable(int page, int size, String[] sort) {
        List<Order> cristeriosOrdenacion = new ArrayList<Order>();
            if (sort[0].contains(",")) {
                for (String cristerioOrdenacion : sort) {
                    String [] order = cristerioOrdenacion.split(",");
                    if (order.length > 1) {
                        cristeriosOrdenacion.add(new Order(Direction.fromString(order[1]), order[0]));
                    } else {
                        cristeriosOrdenacion.add(new Order(Direction.fromString("asc"), order[0]));
                    }
                }
            } else {
                cristeriosOrdenacion.add(new Order(Direction.fromString(sort[1]), sort[0]));
            }
            Sort sorts = Sort.by(cristeriosOrdenacion);
        return PageRequest.of(page, size, sorts);
    }


}
