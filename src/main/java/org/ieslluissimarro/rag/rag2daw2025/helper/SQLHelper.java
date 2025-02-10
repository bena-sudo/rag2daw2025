package org.ieslluissimarro.rag.rag2daw2025.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SQLHelper {

    // http.....com/api/rag/v1/chats/filter?operacion=filtro&filterBy=user&valor1=manolo
    // http.....com/api/rag/v1/chats/filter?operacion=filtro&filterBy=user&valor1=manolo&filterBy2=context&valor2=1
    // http.....com/api/rag/v1/chats/filter?operacion=rango&filterBy=fecha&valor1=20-01-2024&valor2=5-2-2025
    // mostrar los chats en el rango de valor1 y valor2
    // http.....com/api/rag/v1/chats/filter?operacion=rango&filterBy=fecha&valor1=20-01-2024
    // aqui falta el valor2 por lo que será default el dia actual
    // http.....com/api/rag/v1/chats/filter?operacion=count&filterBy=user&valor1=pepe
    // recuento simple

    public static String builderSentencias(String tableName, Map<String, String> params) {

        String operacion = params.get("operacion");
        String filterBy = params.get("filterBy");
        String filterBy2 = params.get("filterBy2");
        String valor1 = params.get("valor1");
        String valor2 = params.get("valor2");


        if (operacion == null || filterBy == null || valor1 == null) {
            throw new IllegalArgumentException("Parámetros incompletos. Se requieren 'operacion', 'filterBy' y 'valor1'.");
        }

        if (!operacion.equals("rango")) {
            if ((filterBy2 != null && valor2 == null) || (filterBy2 == null && valor2 != null)) {
                throw new IllegalArgumentException("Parámetros incompletos. Se requieren los parámetros secundarios estén completos.");
            }
        }

 

        if ( filterBy != null && filterBy.matches("user")) {
            filterBy = "\"user\"";
        }

        if (filterBy2 != null &&  filterBy2.matches("user")) {
            filterBy2 = "\"user\"";
        }

        switch (operacion.toLowerCase()) {
            case "filtro":
                return buildFilterQuery(tableName, filterBy, valor1);
            case "rango":
                return buildRangeQuery(tableName, filterBy, valor1, valor2);
            case "count":

                if (valor2==null || filterBy2== null) {
                    return buildCountQuery(tableName, filterBy, valor1);
                }
                return buildCountQuery(tableName, filterBy, filterBy2, valor1, valor2);

                
            default:
                throw new IllegalArgumentException("Operación no válida: " + operacion);
        }
       
    }



        private static String buildFilterQuery(String tableName, String filterBy, String valor1) {
        return String.format("SELECT * FROM %s WHERE %s = '%s'", tableName, filterBy, valor1);
    }


    private static String buildRangeQuery(String tableName, String filterBy, String valor1, String valor2) {
        if (valor2 == null) {
            valor2 = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Fecha actual por defecto
        }
        return String.format("SELECT * FROM %s WHERE %s BETWEEN '%s' AND '%s'", tableName, filterBy, valor1, valor2);
    }

    private static String buildCountQuery(String tableName, String filterBy, String valor1) {
        return String.format("SELECT COUNT(*) FROM %s WHERE %s = '%s'", tableName, filterBy, valor1);
    }
    
    private static String buildCountQuery(String tableName, String filterBy, String filterBy2, String valor1, String valor2) {
        return String.format("SELECT COUNT(*) FROM %s WHERE %s = '%s' AND %s = '%s'", 
                             tableName, filterBy, valor1, filterBy2, valor2);
    }
}
