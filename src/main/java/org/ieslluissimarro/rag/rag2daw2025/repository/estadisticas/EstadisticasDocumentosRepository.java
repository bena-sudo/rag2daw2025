package org.ieslluissimarro.rag.rag2daw2025.repository.estadisticas;

import java.util.List;
import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentoDbDocu;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.estadisticas.DocumentoEvolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstadisticasDocumentosRepository extends JpaRepository<DocumentoDbDocu,Long>{
    @Query(value = "SELECT DATE(fecha_creacion) as date, COUNT(*) as count " +
               "FROM documentos " +
               "GROUP BY DATE(fecha_creacion) " +
               "ORDER BY DATE(fecha_creacion)", nativeQuery = true)
    List<DocumentoEvolution> documentosPorDia();

}
