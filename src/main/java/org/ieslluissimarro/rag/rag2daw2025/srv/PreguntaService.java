package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaList;
import org.springframework.data.domain.Sort;

import io.micrometer.common.lang.NonNull;

public interface PreguntaService {

    public PreguntaInfo create(Long idChat, String textoPregunta, String user);

    public PreguntaEdit responderPreguntaChat(Long idPregunta, String user);

    public PreguntaInfo getPreguntaInfoById(Long id);

    public List<PreguntaList> findAllPreguntaList();

    public List<PreguntaList> findAllPreguntaList(Sort sort);

    public @NonNull List<PreguntaDb> findByTextContaining(String texto, Sort sort);

    /*
     * Muestra un mensaje predeterminado al iniciar el chat.
     */
    public PreguntaInfo initialMessageChat(String mENSAJE_INICIAL);
}