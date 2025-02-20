package org.ieslluissimarro.rag.rag2daw2025.srv.impl;


import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PaginationFactory;
import org.ieslluissimarro.rag.rag2daw2025.model.db.EstadoAcreditacionDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EstadoAcreditacionEdit;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.filters.specification.FiltroBusquedaSpecification;
import org.ieslluissimarro.rag.rag2daw2025.repository.EstadoAcreditacionRepository;
import org.ieslluissimarro.rag.rag2daw2025.repository.ModuloRepository;
import org.ieslluissimarro.rag.rag2daw2025.repository.UsuarioRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.EstadoAcreditacionService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.EstadoAcreditacionMapper;
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
public class EstadoAcreditacionServiceImpl implements EstadoAcreditacionService {

    private final EstadoAcreditacionRepository estadoAcreditacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModuloRepository moduloRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public EstadoAcreditacionEdit read(Long id) {
        EstadoAcreditacionDb entity = estadoAcreditacionRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("ESTADO_ACREDITACION_NOT_FOUND", "No se encontró el estado de acreditación con id: " + id));
        return EstadoAcreditacionMapper.INSTANCE.estadoAcreditacionDbEstadoAcreditacionEdit(entity);
    }

    @Override
    public EstadoAcreditacionEdit create(EstadoAcreditacionEdit estadoAcreditacionEdit) {
        if (estadoAcreditacionEdit.getId() !=  null) {
            throw new EntityIllegalArgumentException("ESTADO_ACREDITACION_ID_MISMATCH", "El ID debe ser nulo al crear un nuevo estado de acreditación");
        }
    
        EstadoAcreditacionDb entity = EstadoAcreditacionMapper.INSTANCE.estadoAcreditacionEditEstadoAcreditacionDb(estadoAcreditacionEdit);
    
        if (estadoAcreditacionEdit.getUsuario_id() != null) {
            Long usuarioId = usuarioRepository.findById(estadoAcreditacionEdit.getUsuario_id())
                .orElseThrow(() -> new EntityNotFoundException("USUARIO_NOT_FOUND", "No se encontró el usuario con id: " + estadoAcreditacionEdit.getUsuario_id()))
                .getId();
        
            entity.setUsuario_id(usuarioId);
        }
        
        if (estadoAcreditacionEdit.getAsesor_id() != null) {
            Long asesorId = usuarioRepository.findById(estadoAcreditacionEdit.getAsesor_id())
                .orElseThrow(() -> new EntityNotFoundException("ASESOR_NOT_FOUND", "No se encontró el asesor con id: " + estadoAcreditacionEdit.getAsesor_id()))
                .getId();
            entity.setAsesor_id(asesorId);
        }
        if (estadoAcreditacionEdit.getModulo_id() != null) {
            entity.setModulo(moduloRepository.findById(estadoAcreditacionEdit.getModulo_id())
                .orElseThrow(() -> new EntityNotFoundException("MODULO_NOT_FOUND", "No se encontró el módulo con id: " + estadoAcreditacionEdit.getModulo_id())));
        }
    
        return EstadoAcreditacionMapper.INSTANCE.estadoAcreditacionDbEstadoAcreditacionEdit(estadoAcreditacionRepository.save(entity));
    }

    @Override
public EstadoAcreditacionEdit update(Long id, EstadoAcreditacionEdit estadoAcreditacionEdit) {
    if (!id.equals(estadoAcreditacionEdit.getId())) {
        throw new EntityIllegalArgumentException("ESTADO_ACREDITACION_ID_MISMATCH", "El ID proporcionado no coincide con el ID del estado de acreditacion a actualizar.");
    }

    EstadoAcreditacionDb existingEntity = estadoAcreditacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ESTADO_ACREDITACION_NOT_FOUND_FOR_UPDATE", "No se puede actualizar. El estado de acreditación con ID " + id + " no existe."));

    EstadoAcreditacionMapper.INSTANCE.updateEstadoAcreditacionDbFromEstadoAcreditacionEdit(estadoAcreditacionEdit, existingEntity);

    if (estadoAcreditacionEdit.getUsuario_id() != null) {
        Long usuarioId = usuarioRepository.findById(estadoAcreditacionEdit.getUsuario_id())
            .orElseThrow(() -> new EntityNotFoundException("USUARIO_NOT_FOUND", "No se encontró el usuario con id: " + estadoAcreditacionEdit.getUsuario_id()))
            .getId();
    
        existingEntity.setUsuario_id(usuarioId);
    }
    
    if (estadoAcreditacionEdit.getAsesor_id() != null) {
        Long asesorId = usuarioRepository.findById(estadoAcreditacionEdit.getAsesor_id())
            .orElseThrow(() -> new EntityNotFoundException("ASESOR_NOT_FOUND", "No se encontró el asesor con id: " + estadoAcreditacionEdit.getAsesor_id()))
            .getId();
        existingEntity.setAsesor_id(asesorId);
    }
    if (estadoAcreditacionEdit.getModulo_id() != null) {
        existingEntity.setModulo(moduloRepository.findById(estadoAcreditacionEdit.getModulo_id())
            .orElseThrow(() -> new EntityNotFoundException("MODULO_NOT_FOUND", "No se encontró el módulo con id: " + estadoAcreditacionEdit.getModulo_id())));
    }

    return EstadoAcreditacionMapper.INSTANCE.estadoAcreditacionDbEstadoAcreditacionEdit(estadoAcreditacionRepository.save(existingEntity));
}

    @Override
    public void delete(Long id) {
        if (estadoAcreditacionRepository.existsById(id)) {
            estadoAcreditacionRepository.deleteById(id);
        }
    }

    @Override
    public PaginaResponse<EstadoAcreditacionEdit> findAll(List<String> filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<EstadoAcreditacionEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<EstadoAcreditacionDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<EstadoAcreditacionDb> page = estadoAcreditacionRepository.findAll(filtrosBusquedaSpecification, pageable);
            return EstadoAcreditacionMapper.pageToPaginaResponseEstadoAcreditacionEdit(
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