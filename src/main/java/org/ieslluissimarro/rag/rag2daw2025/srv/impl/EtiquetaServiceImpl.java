package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.filters.specification.FiltroBusquedaSpecification;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PaginationFactory;
import org.ieslluissimarro.rag.rag2daw2025.model.db.EtiquetaDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaList;
import org.ieslluissimarro.rag.rag2daw2025.repository.EtiquetaRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.EtiquetaService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.EtiquetaMapper;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EtiquetaServiceImpl implements EtiquetaService {

    private final EtiquetaRepository etiquetaRepository;
    private final PaginationFactory paginationFactory;

    @Override
    public EtiquetaEdit create(EtiquetaEdit etiquetaEdit) {
        if (etiquetaEdit.getId() != null) {
            throw new EntityIllegalArgumentException("ETIQUETA_ID_MISMATCH",
                    "El ID debe ser nulo al crear una nueva etiqueta.");
        }
        EtiquetaDB entity = EtiquetaMapper.INSTANCE.etiquetaEditToEtiquetaDB(etiquetaEdit);
        return EtiquetaMapper.INSTANCE.etiquetaDBToEtiquetaEdit(etiquetaRepository.save(entity));
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

    @SuppressWarnings("null")
    @Override
    public PaginaResponse<EtiquetaList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            // Configurar criterio de filtrado con Specification
            Specification<EtiquetaDB> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<EtiquetaDB>(
                    peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar: puede producir cualquier de los errores controlados en el
            // catch
            Page<EtiquetaDB> page = etiquetaRepository.findAll(filtrosBusquedaSpecification, pageable);
            // Devolver respuesta
            return EtiquetaMapper.pageToPaginaResponseEtiquetaList(
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
}
