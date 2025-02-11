package org.ieslluissimarro.rag.rag2daw2025.helper;

import java.util.HashMap;
import java.util.Map;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.FiltrosRequest;

public class JsonToMapHelper {


    public static Map<String,String> converter(FiltrosRequest filtros){

        Map<String,String> parametros = new HashMap<>();
        
        parametros.put("filterUser", filtros.getFilterUser());
        parametros.put("filterRango", filtros.getFilterRango());
        parametros.put("filterRespuesta", filtros.getFilterRespuesta());
        parametros.put("filterPregunta", filtros.getFilterPregunta());
        parametros.put("filterFeedback", filtros.getFilterFeedback());
        parametros.put("filterChunk", filtros.getFilterChunk());

        return parametros;
    }

}
