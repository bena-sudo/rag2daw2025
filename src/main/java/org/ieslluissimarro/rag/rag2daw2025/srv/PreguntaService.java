package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaList;
import org.springframework.data.domain.Sort;

import io.micrometer.common.lang.NonNull;


public interface PreguntaService {
    public Optional<PreguntaInfo> getPreguntaInfoById( Long id);
    public List<PreguntaList> findAllPreguntaList();
    public List<PreguntaList> findAllPreguntaList( Sort sort);
    public @NonNull List<PreguntaDb> findByTextContaining( String texto,  Sort sort);

}