package org.ieslluissimarro.rag.rag2daw2025.model;

import org.ieslluissimarro.rag.rag2daw2025.exception.DataValidationException;

import lombok.Value;

@Value
public class IdEntityLong {
    Long value;

    public IdEntityLong(Long id) {
        if (id == null) {
            throw new DataValidationException("ID_NULL", "El ID no puede ser nulo.");
        }
        this.value = id;
    }

    public IdEntityLong(String id) {
        try {
            this.value = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new DataValidationException("ID_FORMAT_INVALID",
                    "El ID debe ser un valor numérico de tipo Long (INT64).");
        }
    }
}