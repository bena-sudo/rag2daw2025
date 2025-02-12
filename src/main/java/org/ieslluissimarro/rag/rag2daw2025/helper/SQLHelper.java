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
            "c.fecha AS chat_fecha, " +
            "c.contexto, " +
            "p.id_pregunta, " +
            "p.\"user\" AS pregunta_user, " +
            "p.texto_pregunta, " +
            "p.texto_respuesta, " +
            "p.fecha AS pregunta_fecha, " +
            "p.feedback, " +
            "p.valorado, " +
            "p.id_chat AS pregunta_id_chat, " +
            "d.id_documentchunk " +
            "FROM " +
            "chats c " +
            "JOIN preguntas p ON c.id_chat = p.id_chat " +
            "LEFT JOIN pregunta_documentchunk pd ON p.id_pregunta = pd.id_pregunta " +
            "LEFT JOIN documentchunks d ON pd.id_documentchunk = d.id_documentchunk ";

    public static String builderSentencias(Map<String, String> params) {

        int i = 0;
        StringBuilder queryFinal = new StringBuilder();
        queryFinal.append(CONSULTA_BASE + " WHERE");

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

                    queryFinal.append(" c.\"user\" = '" + filterUserBy + "' ");
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
                        fechaFinal = "2000-01-01";
                    }
                    if (fechaFinal == null || fechaFinal.matches("null")) {
                        fechaFinal = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }

                    if (i > 0) {
                        queryFinal.append(" AND ");
                    }

                    queryFinal.append(" c.fecha BETWEEN '" + fechaInicial + "' and '" + fechaFinal + "'");
                    i++;
                    break;

                case "filterPregunta":
                    String filterPregunta = params.get("filterPregunta");

                    if (filterPregunta == null) {
                        break;
                    }

                    queryFinal.append(" p.texto_pregunta = '" + filterPregunta + "' ");
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

                    queryFinal.append(" p.texto_respuesta = '" + filterRespuesta + "' ");
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

                    queryFinal.append(" p.feedback = '" + feedback + "' ");
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

                    queryFinal.append(" p.valorado = " + valorado + " ");
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

                    queryFinal.append(" d.id_documentchunk = '" + chunk + "' ");
                    i++;
                    break;

                default:
                    throw new IllegalArgumentException("Operación no válida");
            }

        }

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
