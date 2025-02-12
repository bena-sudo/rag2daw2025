package org.ieslluissimarro.rag.rag2daw2025.repository;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.EstadisticaDocumentalDB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Nonnull;

@Repository
public interface EstadisticasDocumentalRepository
        extends JpaRepository<EstadisticaDocumentalDB, Long>, JpaSpecificationExecutor<EstadisticaDocumentalDB> {
    @Nonnull
    Page<EstadisticaDocumentalDB> findAll(@Nonnull Pageable pageable);

    @Query("SELECT DATE(e.fecha), COUNT(e) FROM EstadisticaDocumentalDB e GROUP BY DATE(e.fecha)")
    List<Object[]> getRevisionesPorFecha();

    @Query("SELECT e.estadoFinal, COUNT(e) FROM EstadisticaDocumentalDB e GROUP BY e.estadoFinal")
    List<Object[]> countDocumentosPorEstado();

    @Query("SELECT e.usuario, AVG(e.tiempoRevision) FROM EstadisticaDocumentalDB e GROUP BY e.usuario")
    List<Object[]> getTiempoRevisionPromedioPorUsuario();

}
