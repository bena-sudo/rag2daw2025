package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PaginationFactory;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.filters.specification.FiltroBusquedaSpecification;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PeticionListadoFiltradoConverter;
import org.ieslluissimarro.rag.rag2daw2025.model.db.CuestionarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.CuestionarioEdit;
import org.ieslluissimarro.rag.rag2daw2025.repository.CuestionarioRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.CuestionarioService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.CuestionarioMapper;
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
public class CuestionarioServiceImpl implements CuestionarioService {

    private final CuestionarioRepository cuestionarioRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public CuestionarioEdit create(CuestionarioEdit cuestionarioEdit) {
        if (cuestionarioEdit.getId() !=  null) {
            throw new EntityIllegalArgumentException("CUESTIONARIO_ID_MISMATCH", "El ID debe ser nulo al crear un nuevo cuestionario");
        }

        CuestionarioDb entity = CuestionarioMapper.INSTANCE.cuestionarioEditCuestionarioDb(cuestionarioEdit);
        return CuestionarioMapper.INSTANCE.cuestionarioDbCuestionarioEdit(cuestionarioRepository.save(entity));
    }

    @Override
    public CuestionarioEdit read(Long id) {
        CuestionarioDb entity = cuestionarioRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("UC_NOT_FOUND", "No se encontró el cuestionario con id: " + id));
        return CuestionarioMapper.INSTANCE.cuestionarioDbCuestionarioEdit(entity);
    }

    @Override
    public CuestionarioEdit update(Long id, CuestionarioEdit cuestionarioEdit) {
        if (!id.equals(cuestionarioEdit.getId())) {
            throw new EntityIllegalArgumentException("CUESTIONARIO_ID_MISMATCH", "El ID proporcionado no coincide con el ID del cuestionario a actualizar.");
        }

        CuestionarioDb existingEntity = cuestionarioRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException("CUESTIONARIO_NOT_FOUND_FOR_UPDATE", "No se puede actualizar. El cuestionario con ID " + id + " no existe."));
        
        CuestionarioMapper.INSTANCE.updateCuestionarioDbFromCuestionarioEdit(cuestionarioEdit, existingEntity);
        return CuestionarioMapper.INSTANCE.cuestionarioDbCuestionarioEdit(cuestionarioRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (cuestionarioRepository.existsById(id)) {
            cuestionarioRepository.deleteById(id);
        }
    }

    @Override
    public PaginaResponse<CuestionarioEdit> findAll(List<String> filter, int page, int size, List<String> sort) 
            throws FiltroException {
        /** 'peticionConverter' está en el constructor del service porque utilizando una buena arquitectura
        toda clase externa al Service que contenga un método a ejecutar debería ser testeable de manera
        independiente y para ello debe de estar en el constructor para poderse mockear.**/
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @SuppressWarnings("null")
    @Override
    public PaginaResponse<CuestionarioEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        /** 'paginationFactory' está en el constructor del service porque utilizando una buena arquitectura
        toda clase externa al Service que contenga un método a ejecutar debería ser testeable de manera
        independiente y para ello debe de estar en el constructor para poderse mockear.**/
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            // Configurar criterio de filtrado con Specification
            Specification<CuestionarioDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<CuestionarioDb>(
                peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar: puede producir cualquier de los errores controlados en el catch
            Page<CuestionarioDb> page = cuestionarioRepository.findAll(filtrosBusquedaSpecification, pageable);
            //Devolver respuesta
            return CuestionarioMapper.pageToPaginaResponseCuestionarioEdit(
                page,
                peticionListadoFiltrado.getListaFiltros(), 
                peticionListadoFiltrado.getSort());
        } catch (JpaSystemException e) {
            String cause="";
            if  (e.getRootCause()!=null){
                if (e.getCause().getMessage()!=null)
                    cause= e.getRootCause().getMessage();
            }
            throw new FiltroException("BAD_OPERATOR_FILTER",
                    "Error: No se puede realizar esa operación sobre el atributo por el tipo de dato", e.getMessage()+":"+cause);
        } catch (PropertyReferenceException e) {
            throw new FiltroException("BAD_ATTRIBUTE_ORDER",
                    "Error: No existe el nombre del atributo de ordenación en la tabla", e.getMessage());
        } catch (InvalidDataAccessApiUsageException e) {
            throw new FiltroException("BAD_ATTRIBUTE_FILTER", "Error: Posiblemente no existe el atributo en la tabla",
                    e.getMessage());
        }
    }
}