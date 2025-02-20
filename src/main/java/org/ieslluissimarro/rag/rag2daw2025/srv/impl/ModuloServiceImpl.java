package org.ieslluissimarro.rag.rag2daw2025.srv.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PaginationFactory;
import org.ieslluissimarro.rag.rag2daw2025.model.db.ModuloDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ModuloEdit;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.filters.specification.FiltroBusquedaSpecification;
import org.ieslluissimarro.rag.rag2daw2025.repository.ModuloRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.ModuloService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.ModuloMapper;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PeticionListadoFiltradoConverter;
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
public class ModuloServiceImpl implements ModuloService {

    private final ModuloRepository moduloRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public ModuloEdit create(ModuloEdit moduloEdit) {
        if (moduloEdit.getId() !=  null) {
            throw new EntityIllegalArgumentException("MODULO_ID_MISMATCH", "El ID debe ser nulo al crear un nuevo modulo");
        }

        ModuloDb entity = ModuloMapper.INSTANCE.moduloEditModuloDb(moduloEdit);
        return ModuloMapper.INSTANCE.moduloDbModuloEdit(moduloRepository.save(entity));
    }

    @Override
    public ModuloEdit read(Long id) {
        ModuloDb entity = moduloRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("MODULO_NOT_FOUND", "No se encontró el modulo con id: " + id));
        return ModuloMapper.INSTANCE.moduloDbModuloEdit(entity);
    }

    @Override
    public ModuloEdit update(Long id, ModuloEdit moduloEdit) {
        if (!id.equals(moduloEdit.getId())) {
            throw new EntityIllegalArgumentException("MODULO_ID_MISMATCH", "El ID proporcionado no coincide con el ID del modulo a actualizar.");
        }

        ModuloDb existingEntity = moduloRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("CUESTIONARIO_NOT_FOUND_FOR_UPDATE", "No se puede actualizar. El cuestionario con ID " + id + " no existe."));
        
        ModuloMapper.INSTANCE.updateModuloDbFromModuloEdit(moduloEdit, existingEntity);
        return ModuloMapper.INSTANCE.moduloDbModuloEdit(moduloRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (moduloRepository.existsById(id)) {
            moduloRepository.deleteById(id);
        }
    }

    // @Override
    // public List<ModuloEdit> getAllModulos() {
    //     return moduloRepository.findAll().stream()
    //         .map(ModuloMapper.INSTANCE::moduloDbModuloEdit)
    //         .collect(Collectors.toList());
    // }

    @Override
    public List<ModuloEdit> getModulosBySector(Long sectorId) {
        return moduloRepository.findBySectorId(sectorId).stream()
            .map(ModuloMapper.INSTANCE::moduloDbModuloEdit)
            .collect(Collectors.toList());
    }

    @Override
    public PaginaResponse<ModuloEdit> findAll(List<String> filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<ModuloEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<ModuloDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<ModuloDb> page = moduloRepository.findAll(filtrosBusquedaSpecification, pageable);
            return ModuloMapper.pageToPaginaResponseModuloEdit(
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