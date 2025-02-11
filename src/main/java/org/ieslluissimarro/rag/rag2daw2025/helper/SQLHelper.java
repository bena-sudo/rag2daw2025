package org.ieslluissimarro.rag.rag2daw2025.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SQLHelper {

    // http.....com/api/rag/v1/chats/filter?operacion=filterUser,rango&column=user&filterUserBy=manolo

    // operacion=filtroP,filtroR,filtroUser&filterUserBy=manolo&filterRespuestaBy=Hola&filterPreguntaBy=Pregunta

    public static String builderSentencias(String tableName, Map<String, String> params) {

        String operacion = params.get("operacion");
        ArrayList<String> operacionesList = new ArrayList<>(Arrays.asList(operacion.split(",")));

        int i = 0;
        StringBuilder queryFinal = new StringBuilder();
        for (String op : operacionesList) {
            if (i > 0) {
                queryFinal.append(" AND ");
            } else {
                queryFinal.append("SELECT * FROM " + tableName + " ");
            }

            switch (op) {
                case "filterUser":
                    String filterUserBy = params.get("filterUserBy");
                    if (filterUserBy == null) {
                        throw new IllegalArgumentException(
                                "Parámetros incompletos en la petición. -Filter by user");
                    }

                    queryFinal.append("WHERE \"user\" = '" + filterUserBy + "' ");
                    break;
                case "rango":
                    String fechaInical = params.get("fechaInicial");
                    String fechaFinal = params.get("fechaFinal");
                    if (fechaInical == null) {
                        fechaFinal= "1940-01-01";
                    }
                    if (fechaFinal == null) {
                        fechaFinal = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }

                    queryFinal.append("WHERE fecha BETWEEN '" + fechaInical + "' and '" + fechaFinal + "'");

                    break;

                case "filterP":
                    String filterPregunta = params.get("filterPreguntaBy");

                    if (filterPregunta == null) {
                        throw new IllegalArgumentException(
                                "Parámetros incompletos en la petición. -Pregunta");
                    }

                    queryFinal.append("WHERE texto_pregunta = '" + filterPregunta + "' ");
                    break;

                case "filterR":
                    String filterRespuesta = params.get("filterRespuestaBy");

                    if (filterRespuesta == null) {
                        throw new IllegalArgumentException(
                                "Parámetros incompletos en la petición. -Respuesta");
                    }

                    queryFinal.append("WHERE texto_respuesta = '" + filterRespuesta + "' ");
                    break;

                case "filterFeedback":
                    String feedback = params.get("feedbackValue");

                    if (feedback == null) {
                        throw new IllegalArgumentException(
                                "Parámetros incompletos en la petición. -Feedback");
                    }

                    queryFinal.append("WHERE feedback = '" + feedback + "' ");
                    break;

                case "filterChunk":
                    String chunk = params.get("filtChunkBy");

                    if (chunk == null) {
                        throw new IllegalArgumentException(
                                "Parámetros incompletos en la petición. -Chunk");
                    }

                    queryFinal.append("WHERE id_documentchunk = '" + chunk + "' ");
                    break;

                default:
                    throw new IllegalArgumentException("Operación no válida: " + operacion);
            }
            i++;
        }

        return queryFinal.toString();

    }

    public static String selectDistinctString(String tableName, String column) {
        return String.format("SELECT DISTINCT %s FROM %s", column, tableName);
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

    private static String buildCountQuery(String tableName, String filterBy, String filterBy2, String valor1,
            String valor2) {
        return String.format("SELECT COUNT(*) FROM %s WHERE %s = '%s' AND %s = '%s'",
                tableName, filterBy, valor1, filterBy2, valor2);
    }

    public static String selectContextoCountGrouped(String campo) {
        return String.format("SELECT contexto, COUNT(*) AS total FROM %s GROUP BY contexto ORDER BY total DESC ",
                campo);
    }
}
