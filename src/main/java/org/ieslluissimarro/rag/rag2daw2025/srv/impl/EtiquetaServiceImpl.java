package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.model.db.EtiquetaDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaEdit;
import org.ieslluissimarro.rag.rag2daw2025.repository.EtiquetaRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.EtiquetaService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.EtiquetaMapper;

public class EtiquetaServiceImpl implements EtiquetaService {

    private final EtiquetaRepository etiquetaRepository;

    public EtiquetaServiceImpl(EtiquetaRepository etiquetaRepository) {
        this.etiquetaRepository = etiquetaRepository;
    }

    @Override
    public EtiquetaEdit create(EtiquetaEdit etiquetaEdit) {
        if (etiquetaEdit.getId() != null) {
            throw new EntityIllegalArgumentException("ETIQUETA_ID_MISMATCH",
                    "El ID debe ser nulo al crear una nueva etiqueta.");
        }
        EtiquetaDB entity = EtiquetaMapper.INSTANCE.etiquetaEditToEtiquetaDB(etiquetaEdit);
        return EtiquetaMapper.INSTANCE.etiquetaDBToEtiquetaEdit(entity);
    }

    @Override
    public EtiquetaEdit read(Long id) {
        EtiquetaDB entity = etiquetaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ETIQUETA_NOT_FOUND",
                        "La etiqueta con ID " + id + " no existe."));
        return EtiquetaMapper.INSTANCE.etiquetaDBToEtiquetaEdit(entity);
    }

    @Override
    public EtiquetaEdit update(Long id, EtiquetaEdit etiquetaEdit) {
        if (!id.equals(etiquetaEdit.getId())) {
            throw new EntityIllegalArgumentException("ETIQUETA_ID_MISMATCH",
                    "El ID proporcionado no coincide con el ID de la etiqueta.");
        }
        EtiquetaDB existingEntity = etiquetaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ETIQUETA_NOT_FOUND_FOR_UPDATE",
                        "No se puede actualizar. La etiqueta con ID " + id + " no existe."));
        EtiquetaMapper.INSTANCE.updateEtiquetaDBFrometiquetaEdit(etiquetaEdit, existingEntity);
        return EtiquetaMapper.INSTANCE.etiquetaDBToEtiquetaEdit(etiquetaRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (etiquetaRepository.existsById(id)) {
            etiquetaRepository.deleteById(id);
        }
    }
}
