package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaList;


public interface PreguntaService {

    public PreguntaEdit create(Long idChat, String textoPregunta, String user);

    public PreguntaEdit update(Long id, PreguntaEdit preguntaEdit);

    public PreguntaInfo getPreguntaInfoById(Long id);

    public List<PreguntaList> findAllPreguntaList();

}