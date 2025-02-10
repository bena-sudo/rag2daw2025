package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.filters.specification.FiltroBusquedaSpecification;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PaginationFactory;
import org.ieslluissimarro.rag.rag2daw2025.model.db.EstadisticasDocumentalDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EstadisticasDocumentalList;
import org.ieslluissimarro.rag.rag2daw2025.repository.EstadisticasDocumentalRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.EstadisticasDocumentalService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.EstadisticasDocumentalMapper;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

@Service
public class EstadisticasDocumentalServiceImpl implements EstadisticasDocumentalService {

    private final PaginationFactory paginationFactory;
    private final EstadisticasDocumentalRepository estadisticasRepository;

    public EstadisticasDocumentalServiceImpl(PaginationFactory paginationFactory,
            EstadisticasDocumentalRepository estadisticasRepository) {
        this.paginationFactory = paginationFactory;
        this.estadisticasRepository = estadisticasRepository;
    }

    @SuppressWarnings("null")
    @Override
    public PaginaResponse<EstadisticasDocumentalList> findAll(PeticionListadoFiltrado peticionListadoFiltrado)
            throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            // Configurar criterio de filtrado con Specification
            Specification<EstadisticasDocumentalDB> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<EstadisticasDocumentalDB>(
                    peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar: puede producir cualquier de los errores controlados en el
            // catch
            Page<EstadisticasDocumentalDB> page = estadisticasRepository.findAll(filtrosBusquedaSpecification,
                    pageable);
            // Devolver respuesta
            return EstadisticasDocumentalMapper.pageToPaginaResponseEstadisticasDocumentalList(
                    page,
                    peticionListadoFiltrado.getListaFiltros(),
                    peticionListadoFiltrado.getSort());
        } catch (JpaSystemException e) {
            String cause = "";
            if (e.getRootCause() != null) {
                if (e.getCause().getMessage() != null)
                    cause = e.getRootCause().getMessage();
            }
            throw new FiltroException("BAD_OPERATOR_FILTER",
                    "Error: No se puede realizar esa operación sobre el atributo por el tipo de dato",
                    e.getMessage() + ":" + cause);
        } catch (PropertyReferenceException e) {
            throw new FiltroException("BAD_ATTRIBUTE_ORDER",
                    "Error: No existe el nombre del atributo de ordenación en la tabla", e.getMessage());
        } catch (InvalidDataAccessApiUsageException e) {
            throw new FiltroException("BAD_ATTRIBUTE_FILTER", "Error: Posiblemente no existe el atributo en la tabla",
                    e.getMessage());
        }
    }

    @Override
    public List<Object[]> getRevisionesPorFecha() {
        return estadisticasRepository.getRevisionesPorFecha();
    }

    @Override
    public List<Object[]> countDocumentosPorEstado() {
        return estadisticasRepository.countDocumentosPorEstado();
    }

    @Override
    public List<Object[]> getTiempoRevisionPromedioPorUsuario() {
        return estadisticasRepository.getTiempoRevisionPromedioPorUsuario();
    }

}
