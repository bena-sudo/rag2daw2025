package org.ieslluissimarro.rag.rag2daw2025.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SQLHelper {

    private static final String CONSULTA_BASE = "SELECT " +
            "c.id_chat, " +
            "c.\"user\" AS chat_user, " +
            "c.fecha::date AS fecha, " +
            "c.contexto, " +
            "p.id_pregunta, " +
            "p.\"user\" AS pregunta_user, " +
            "p.texto_pregunta, " +
            "p.texto_respuesta, " +
            "p.fecha::date AS pregunta_fecha, " +
            "p.feedback, " +
            "p.valorado, " +
            "p.id_chat AS pregunta_id_chat, " +
            "d.id_documentchunk " +
            "FROM " +
            "chats c " +
            "LEFT JOIN preguntas p ON c.id_chat = p.id_chat " +
            "LEFT JOIN pregunta_documentchunk pd ON p.id_pregunta = pd.id_pregunta " +
            "LEFT JOIN documentchunks d ON pd.id_documentchunk = d.id_documentchunk ";

    public static String builderSentencias(Map<String, String> params, String groupBy, Boolean historic) {

        int i = 0;
        StringBuilder queryFinal = new StringBuilder();


        if (groupBy != null && !groupBy.matches("none")) {
          
            if (groupBy.equals("user")) {
                groupBy="chat_user";
            }


            if (historic) {
                queryFinal.append("SELECT TO_CHAR(fecha, 'YYYY-MM-DD') AS fecha, "+groupBy+", COUNT(*) AS cantidad FROM ( " +CONSULTA_BASE);

            }else{
                queryFinal.append("SELECT "+groupBy+", COUNT(*) AS cantidad FROM ( " +CONSULTA_BASE);

            }

        
            
        }else{
            queryFinal.append(CONSULTA_BASE);
        }


        

        for (String op : params.keySet()) {
            switch (op) {
                case "filterUser":
                    String filterUserBy = params.get("filterUser");
                    if (filterUserBy == null) {
                        break;
                    }

                    if (i > 0) {
                        queryFinal.append(" AND ");
                    }

                    queryFinal.append(" WHERE c.\"user\" = '" + filterUserBy + "' ");
                    i++;
                    break;
                case "filterRango":

                    String fechas = params.get("filterRango");

                    if (fechas == null) {
                        break;
                    }

                    String[] parts = fechas.split(",");
                    String fechaInicial = parts[0];
                    String fechaFinal = parts[1];

                    if (fechaInicial == null || fechaInicial.matches("null")) {
                        fechaInicial = "2000-01-01";
                    }
                    if (fechaFinal == null || fechaFinal.matches("null")) {
                        fechaFinal = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }

                    if (i > 0) {
                        queryFinal.append(" AND ");
                    }

                    queryFinal.append(" WHERE c.fecha BETWEEN '" + fechaInicial + "' and '" + fechaFinal + "'");
                    i++;
                    break;

                case "filterPregunta":
                    String filterPregunta = params.get("filterPregunta");

                    if (filterPregunta == null) {
                        break;
                    }

                    queryFinal.append(" WHERE p.texto_pregunta = '" + filterPregunta + "' ");
                    i++;
                    break;

                case "filterRespuesta":
                    String filterRespuesta = params.get("filterRespuesta");

                    if (filterRespuesta == null) {
                        break;
                    }

                    if (i > 0) {
                        queryFinal.append(" AND ");
                    }

                    queryFinal.append(" WHERE p.texto_respuesta = '" + filterRespuesta + "' ");
                    i++;
                    break;

                case "filterFeedback":
                    String feedback = params.get("filterFeedback");

                    if (feedback == null) {
                        break;
                    }

                    if (i > 0) {
                        queryFinal.append(" AND ");
                    }

                    queryFinal.append(" WHERE p.feedback = '" + feedback + "' ");
                    i++;
                    break;

                case "filterValorado":
                    String valorado = params.get("filterValorado");

                    if (valorado == null) {
                        break;
                    }

                    if (i > 0) {
                        queryFinal.append(" AND ");
                    }

                    queryFinal.append(" WHERE p.valorado = " + valorado + " ");
                    i++;
                    break;
                case "filterChunk":
                    String chunk = params.get("filterChunk");

                    if (chunk == null) {
                        break;
                    }

                    if (i > 0) {
                        queryFinal.append(" AND ");
                    }

                    queryFinal.append(" WHERE d.id_documentchunk = '" + chunk + "' ");
                    i++;
                    break;

                default:
                    throw new IllegalArgumentException("Operación no válida");
            }

        }

        if (groupBy != null && !groupBy.matches("none")) {  
            queryFinal.append(" ) AS subquery GROUP BY "+ groupBy);

            if (historic) {
                queryFinal.append(", fecha ORDER BY fecha");
            }

            System.out.println( "\n"+queryFinal);
            return queryFinal.toString();
        }
        System.out.println("\n"+queryFinal);

        return queryFinal.toString();
    }

    public static String selectDistinctString(String tableName, String column) {
        return String.format("SELECT DISTINCT %s FROM %s", column, tableName);
    }

    public static String selectContextoCountGrouped(String campo) {
        return String.format("SELECT contexto, COUNT(*) AS total FROM %s GROUP BY contexto ORDER BY total DESC ",
                campo);
    }
}
