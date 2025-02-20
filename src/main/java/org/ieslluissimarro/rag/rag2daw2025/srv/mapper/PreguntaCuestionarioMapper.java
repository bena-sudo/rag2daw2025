package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaCuestionarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.TipoPreguntaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.CuestionarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaCuestionarioEdit;

@Mapper
public interface PreguntaCuestionarioMapper {
    PreguntaCuestionarioMapper INSTANCE = Mappers.getMapper(PreguntaCuestionarioMapper.class);

    @Mapping(source = "cuestionario.id", target = "cuestionarioId")
    @Mapping(source = "tipo.id", target = "tipoId")
    @Mapping(source = "siguienteSi.id", target = "siguienteSiId")
    @Mapping(source = "siguienteNo.id", target = "siguienteNoId")
    PreguntaCuestionarioEdit preguntaDbPreguntaEdit(PreguntaCuestionarioDb preguntaDb);

    @Mapping(source = "cuestionarioId", target = "cuestionario.id")
    @Mapping(source = "tipoId", target = "tipo.id")
    @Mapping(source = "siguienteSiId", target = "siguienteSi.id")
    @Mapping(source = "siguienteNoId", target = "siguienteNo.id")
    PreguntaCuestionarioDb preguntaEditPreguntaDb(PreguntaCuestionarioEdit preguntaEdit);

    void updatePreguntaDbFromPreguntaEdit(PreguntaCuestionarioEdit preguntaEdit, @MappingTarget PreguntaCuestionarioDb preguntaDb);

    // Métodos de mapeo para las relaciones
    default CuestionarioDb mapCuestionarioIdToCuestionario(Long cuestionarioId) {
        if (cuestionarioId == null) {
            return null;
        }
        CuestionarioDb cuestionario = new CuestionarioDb();
        cuestionario.setId(cuestionarioId);
        return cuestionario;
    }

    default TipoPreguntaDb mapTipoIdToTipo(Long tipoId) {
        if (tipoId == null) {
            return null;
        }
        TipoPreguntaDb tipo = new TipoPreguntaDb();
        tipo.setId(tipoId);
        return tipo;
    }

    default PreguntaCuestionarioDb mapSiguienteSiIdToPregunta(Long siguienteSiId) {
        if (siguienteSiId == null) {
            return null;
        }
        PreguntaCuestionarioDb siguienteSi = new PreguntaCuestionarioDb();
        siguienteSi.setId(siguienteSiId);
        return siguienteSi;
    }

    default PreguntaCuestionarioDb mapSiguienteNoIdToPregunta(Long siguienteNoId) {
        if (siguienteNoId == null) {
            return null;
        }
        PreguntaCuestionarioDb siguienteNo = new PreguntaCuestionarioDb();
        siguienteNo.setId(siguienteNoId);
        return siguienteNo;
    }
}
