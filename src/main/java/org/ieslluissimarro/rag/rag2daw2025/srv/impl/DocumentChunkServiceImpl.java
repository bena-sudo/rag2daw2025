package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentChunkDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentChunkEdit;
import org.ieslluissimarro.rag.rag2daw2025.repository.DocumentoChunkEditRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.DocumentChunkService;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DocumentChunkServiceImpl implements DocumentChunkService{
    
    private final DocumentoChunkEditRepository documentoChunkEditRepository;

    public DocumentChunkServiceImpl(DocumentoChunkEditRepository documentoChunkEditRepository){
        this.documentoChunkEditRepository = documentoChunkEditRepository;
    }

    @Override
    public DocumentChunkEdit create(DocumentChunkEdit documentChunkEdit) {
        if (documentoChunkEditRepository.existsById(documentChunkEdit.getId())) {
            throw new EntityAlreadyExistsException("DOCUMENTCHUNK_ALREADY_EXIST",
                "El chunk con ID " + documentChunkEdit.getId() + " ya existe.");
        }
        DocumentChunkDB entity = DocumentoChunkMapper.INSTANCE.alumnoEditToAlumnoEditDb(alumnoEdit);
        return AlumnoMapper.INSTANCE.alumnoEditDbToAlumnoEdit(alumnoRepository.save(entity));
    }

    @Override
    public AlumnoEdit read(String dni) {
        AlumnoEditDb entity = alumnoRepository.findById(dni)
            .orElseThrow(() -> new EntityNotFoundException("STUDENT_NOT_FOUND",
                "No se encontró el alumno con DNI " + dni));
        return AlumnoMapper.INSTANCE.alumnoEditDbToAlumnoEdit(entity);
    }

    @Override
    public AlumnoEdit update(String dni, AlumnoEdit alumnoEdit) {
        if (!dni.equals(alumnoEdit.getDni())) { // Evitamos errores e inconsistencias en la lógica de negocio
            throw new EntityIllegalArgumentException("STUDENT_DNI_MISMATCH",
            "El DNI proporcionado no coincide con el DNI del alumno.");
        }

        AlumnoEditDb existingEntity = alumnoRepository.findById(dni)
            .orElseThrow(() -> new EntityNotFoundException("STUDENT_NOT_FOUND_FOR_UPDATE",
                "No se puede actualizar. El alumno con DNI " + dni + " no existe."));

        AlumnoMapper.INSTANCE.updateAlumnoEditDbFromAlumnoEdit(alumnoEdit, existingEntity);
        return AlumnoMapper.INSTANCE.alumnoEditDbToAlumnoEdit(alumnoRepository.save(existingEntity));
    }

    @Override
    public void delete(String dni) {
        if (alumnoRepository.existsById(dni)) {
            alumnoRepository.deleteById(dni);
        }
    }
}
