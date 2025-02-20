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
import org.ieslluissimarro.rag.rag2daw2025.model.db.RespuestaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.RespuestaEdit;
import org.ieslluissimarro.rag.rag2daw2025.repository.RespuestaRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.RespuestaService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.RespuestaMapper;
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
public class RespuestaServiceImpl implements RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public RespuestaEdit create(RespuestaEdit respuestaEdit) {
        if (respuestaEdit.getId() !=  null) {
            throw new EntityIllegalArgumentException("RESPUESTA_ID_MISMATCH", "El ID debe ser nulo al crear una nueva respuesta");
        }

        RespuestaDb entity = RespuestaMapper.INSTANCE.respuestaEditRespuestaDb(respuestaEdit);
        return RespuestaMapper.INSTANCE.respuestaDbRespuestaEdit(respuestaRepository.save(entity));
    }

    @Override
    public RespuestaEdit read(Long id) {
        RespuestaDb entity = respuestaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("RESPUESTA_NOT_FOUND", "No se encontró la respuesta id: " + id));
        return RespuestaMapper.INSTANCE.respuestaDbRespuestaEdit(entity);
    }


    @Override
    public void delete(Long id) {
        if (respuestaRepository.existsById(id)) {
            respuestaRepository.deleteById(id);
        }
    }

    // @Override
    // public List<RespuestaEdit> getAllRespuestas() {
    //     return respuestaRepository.findAll().stream()
    //         .map(RespuestaMapper.INSTANCE::respuestaDbRespuestaEdit)
    //         .collect(Collectors.toList());
    // }

    @Override
    public PaginaResponse<RespuestaEdit> findAll(List<String> filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<RespuestaEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<RespuestaDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<RespuestaDb> page = respuestaRepository.findAll(filtrosBusquedaSpecification, pageable);
            return RespuestaMapper.pageToPaginaResponseRespuestaEdit(
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

    @Override
    public List<RespuestaDb> createRespuestas(List<RespuestaEdit> respuestas) {
        List<RespuestaDb> respuestasDb = RespuestaMapper.INSTANCE.respuestaEditToRespuestaDb(respuestas);
        return respuestaRepository.saveAll(respuestasDb);
    }
}