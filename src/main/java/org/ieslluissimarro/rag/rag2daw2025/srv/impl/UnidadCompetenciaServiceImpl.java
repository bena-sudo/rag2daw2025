package org.ieslluissimarro.rag.rag2daw2025.srv.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityAlreadyExistsException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PaginationFactory;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PeticionListadoFiltradoConverter;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UnidadCompetenciaDb;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.filters.specification.FiltroBusquedaSpecification;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UnidadCompetenciaEdit;
import org.ieslluissimarro.rag.rag2daw2025.repository.UnidadCompetenciaRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.UnidadCompetenciaService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.UnidadCompetenciaMapper;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UnidadCompetenciaServiceImpl implements UnidadCompetenciaService {

    private final UnidadCompetenciaRepository unidadCompetenciaRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public UnidadCompetenciaEdit create(UnidadCompetenciaEdit unidadCompetenciaEdit) {
        if (unidadCompetenciaRepository.existsById(unidadCompetenciaEdit.getId())) {
            throw new EntityAlreadyExistsException("STUDENT_ALREADY_EXIST",
                    "La unidad de competencia con ID " + unidadCompetenciaEdit.getId() + " ya existe.");
        }
        UnidadCompetenciaDb entity = UnidadCompetenciaMapper.INSTANCE
                .unidadCompetenciaEditToUnidadCompetenciaDb(unidadCompetenciaEdit);
        return UnidadCompetenciaMapper.INSTANCE.unidadCompetenciaDbToUnidadCompetenciaEdit(unidadCompetenciaRepository.save(entity));
    }

    @Override
    public UnidadCompetenciaEdit read(String id) {
        UnidadCompetenciaDb entity = unidadCompetenciaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("UC_NOT_FOUND", "No se encontró la unidad de competencia con id: " + id));
        return UnidadCompetenciaMapper.INSTANCE.unidadCompetenciaDbToUnidadCompetenciaEdit(entity);
                
    }

    @Override
    public UnidadCompetenciaEdit update(String id, UnidadCompetenciaEdit unidadCompetenciaEdit) {
        if (!id.equals(unidadCompetenciaEdit.getId())) {//Evitamos errores e inconsistencias en la lógica de negocio
            throw new EntityIllegalArgumentException("UC_ID_MISMATCH", "El ID de la unidad de competencia proporcionada no coincide con el ID de la unidad de competencia a actualizar.");
        }
        UnidadCompetenciaDb existingEntity = unidadCompetenciaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("UC_NOT_FOUND_FOR_UPDATE", "No se puede actualizar. La unidad de competencia con ID" + id + " no existe"));
        UnidadCompetenciaMapper.INSTANCE.updateAlumnoDbFromAlumnoEdit(unidadCompetenciaEdit, existingEntity);
        return UnidadCompetenciaMapper.INSTANCE.unidadCompetenciaDbToUnidadCompetenciaEdit(unidadCompetenciaRepository.save(existingEntity));
    }

    @Override
    public void delete(String id) {
        if (unidadCompetenciaRepository.existsById(id)) {
            unidadCompetenciaRepository.deleteById(id);
        }
    }
    
    // @Override
    // public List<UnidadCompetenciaEdit> getAllUnidadCompetencias() {
    //     return unidadCompetenciaRepository.findAll().stream()
    //         .map(UnidadCompetenciaMapper.INSTANCE::unidadCompetenciaDbToUnidadCompetenciaEdit)
    //         .collect(Collectors.toList());
    // }

    @Override
    public List<UnidadCompetenciaEdit> getUnidadesCompetenciaByModulo(Long moduloId) {
        return unidadCompetenciaRepository.findByModuloId(moduloId).stream()
            .map(UnidadCompetenciaMapper.INSTANCE::unidadCompetenciaDbToUnidadCompetenciaEdit)
            .collect(Collectors.toList());
    }

    @Override
    public PaginaResponse<UnidadCompetenciaEdit> findAll(List<String> filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<UnidadCompetenciaEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<UnidadCompetenciaDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<UnidadCompetenciaDb> page = unidadCompetenciaRepository.findAll(filtrosBusquedaSpecification, pageable);
            return UnidadCompetenciaMapper.pageToPaginaResponseUnidadCompetenciaEdit(
                page,
                peticionListadoFiltrado.getListaFiltros(), 
                peticionListadoFiltrado.getSort());
        } catch (JpaSystemException e) {
            @SuppressWarnings("null")
            String cause = e.getRootCause() != null ? e.getRootCause().getMessage() : "";
            throw new FiltroException("BAD_OPERATOR_FILTER",
                    "Error: No se puede realizar esa operación sobre el atributo por el tipo de dato", e.getMessage() + ":" + cause);
        } catch (PropertyReferenceException e) {
            throw new FiltroException("BAD_ATTRIBUTE_ORDER",
                    "Error: No existe el nombre del atributo de ordenación en la tabla", e.getMessage());
        } catch (InvalidDataAccessApiUsageException e) {
            throw new FiltroException("BAD_ATTRIBUTE_FILTER", "Error: Posiblemente no existe el atributo en la tabla",
                    e.getMessage());
        }
    }
}
