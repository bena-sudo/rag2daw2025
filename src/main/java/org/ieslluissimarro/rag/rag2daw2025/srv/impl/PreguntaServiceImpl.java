package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;


import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaList;
import org.ieslluissimarro.rag.rag2daw2025.repository.PreguntaRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.PreguntaService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mappers.PreguntaMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PreguntaServiceImpl implements PreguntaService{

        private final PreguntaRepository preguntaRepository;

    @Override
    public PreguntaInfo getPreguntaInfoById(Long id) {
        PreguntaDb preguntaDb = preguntaRepository.findById(id)
        .orElseThrow(()-> new EntityNotFoundException("PREGUNTA_NOT_FOUND", "No se encotnró la pregunta con id "+ id));

        return PreguntaMapper.INSTANCE.PreguntaDbAPreguntaInfo(preguntaDb);
    }

    @Override
    public List<PreguntaList> findAllPreguntaList() {
            List<PreguntaDb> listaChatList = preguntaRepository.findAll();
        return PreguntaMapper.INSTANCE.preguntasAPreguntaList(listaChatList);

    }

    @Override
    public List<PreguntaList> findAllPreguntaList(Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllPreguntaList'");
    }

    @Override
    public List<PreguntaDb> findByTextContaining(String text, Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByTextContaining'");
    }

    @Override
    public PreguntaEdit create(Long idChat, String textoPregunta, String user) {
            PreguntaEdit preguntaEdit = new PreguntaEdit(null, idChat, user, textoPregunta, "", "NORMAL", false);
            PreguntaDb entity = PreguntaMapper.INSTANCE.PreguntaEditAPreguntaDb(preguntaEdit);
            entity = preguntaRepository.save(entity);

            return PreguntaMapper.INSTANCE.PreguntaDbAPreguntaEdit(entity);

    }

    @Override
    public PreguntaEdit responderPreguntaChat(Long idPregunta, String user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'responderPreguntaChat'");
    }

    @Override
    public PreguntaEdit update(Long id, PreguntaEdit preguntaEdit) {
        if (!id.equals(preguntaEdit.getIdPregunta())) {
            throw new EntityIllegalArgumentException("ID_PREGUNTA_MISMATCH", "El id proporcionado no coincide con el id de la pregunta");
        }

        PreguntaDb existingEnity = preguntaRepository.findById(id)
        .orElseThrow(()-> new EntityNotFoundException("PREGUNTA_NOT_FOUND", "No se puede actualizar, el chat con el id:"+ id +" no existe."));

        PreguntaMapper.INSTANCE.updatePreguntaDbFromPreguntaEdit(preguntaEdit,existingEnity);
        return PreguntaMapper.INSTANCE.PreguntaDbAPreguntaEdit(preguntaRepository.save(existingEnity));
    }
    
}
