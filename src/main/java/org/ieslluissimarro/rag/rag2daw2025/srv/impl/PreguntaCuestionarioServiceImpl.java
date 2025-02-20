package org.ieslluissimarro.rag.rag2daw2025.srv.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaCuestionarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaCuestionarioEdit;
import org.ieslluissimarro.rag.rag2daw2025.repository.PreguntaCuestionarioRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.PreguntaCuestionarioService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.PreguntaCuestionarioMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PreguntaCuestionarioServiceImpl implements PreguntaCuestionarioService {

    private final PreguntaCuestionarioRepository preguntaRepository;

    @Override
    public PreguntaCuestionarioEdit create(PreguntaCuestionarioEdit preguntaEdit) {
        if (preguntaEdit.getId() !=  null) {
            throw new EntityIllegalArgumentException("PREGUNTA_ID_MISMATCH", "El ID debe ser nulo al crear una nueva pregunta");
        }

        PreguntaCuestionarioDb entity = PreguntaCuestionarioMapper.INSTANCE.preguntaEditPreguntaDb(preguntaEdit);
        return PreguntaCuestionarioMapper.INSTANCE.preguntaDbPreguntaEdit(preguntaRepository.save(entity));
    }

    @Override
    public PreguntaCuestionarioEdit read(Long id) {
        PreguntaCuestionarioDb entity = preguntaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("PREGUNTA_NOT_FOUND", "No se encontró la pregunta con id: " + id));
        return PreguntaCuestionarioMapper.INSTANCE.preguntaDbPreguntaEdit(entity);
    }

    @Override
    public PreguntaCuestionarioEdit update(Long id, PreguntaCuestionarioEdit preguntaEdit) {
        if (!id.equals(preguntaEdit.getId())) {
            throw new EntityIllegalArgumentException("CUESTIONARIO_ID_MISMATCH", "El ID proporcionado no coincide con el ID del cuestionario a actualizar.");
        }

        PreguntaCuestionarioDb existingEntity = preguntaRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("CUESTIONARIO_NOT_FOUND_FOR_UPDATE", "No se puede actualizar. El cuestionario con ID " + id + " no existe."));
        
        PreguntaCuestionarioMapper.INSTANCE.updatePreguntaDbFromPreguntaEdit(preguntaEdit, existingEntity);
        return PreguntaCuestionarioMapper.INSTANCE.preguntaDbPreguntaEdit(preguntaRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (preguntaRepository.existsById(id)) {
            preguntaRepository.deleteById(id);
        }
    }

    @Override
    public List<PreguntaCuestionarioEdit> getAllPreguntas() {
        return preguntaRepository.findAll().stream()
            .map(PreguntaCuestionarioMapper.INSTANCE::preguntaDbPreguntaEdit)
            .collect(Collectors.toList());
    }

    @Override
    public List<PreguntaCuestionarioEdit> obtenerPreguntasPorCuestionario(Long cuestionarioId) {
        return preguntaRepository.findByCuestionarioId(cuestionarioId).stream()
            .map(PreguntaCuestionarioMapper.INSTANCE::preguntaDbPreguntaEdit)
            .collect(Collectors.toList());
    }
}