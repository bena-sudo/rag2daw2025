package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.filters.specification.FiltroBusquedaSpecification;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PaginationFactory;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PeticionListadoFiltradoConverter;
import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentoChunkDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkList;
import org.ieslluissimarro.rag.rag2daw2025.repository.DocumentoChunkEditRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.DocumentoChunkService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.DocumentoChunkMapper;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

@Service
public class DocumentoChunkServiceImpl implements DocumentoChunkService{
    
    private final DocumentoChunkEditRepository documentoChunkEditRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    public DocumentoChunkServiceImpl(DocumentoChunkEditRepository documentoChunkEditRepository,
                                PaginationFactory paginationFactory,
                                PeticionListadoFiltradoConverter peticionConverter){
        this.documentoChunkEditRepository = documentoChunkEditRepository;
        this.paginationFactory = paginationFactory;
        this.peticionConverter = peticionConverter;
    }

    @Override
    public DocumentoChunkEdit create(DocumentoChunkEdit documentoChunkEdit) {
        DocumentoChunkDB entity = DocumentoChunkMapper.INSTANCE.documentoChunkEditToDocumentoChunkDB(documentoChunkEdit);
        return DocumentoChunkMapper.INSTANCE.documentoChunkDBToDocumentoChunkEdit(documentoChunkEditRepository.save(entity));
    }

    @Override
    public DocumentoChunkInfo read(Long id) {
        DocumentoChunkDB entity = documentoChunkEditRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("DOCUMENTCHUNK_NOT_FOUND",
                "No se encontró el chunk con ID " + id));
        return DocumentoChunkMapper.INSTANCE.documentoChunkDBToDocumentoChunkInfo(entity);
    }

    @Override
    public DocumentoChunkEdit update(Long id, DocumentoChunkEdit documentoChunkEdit) {
        if (!id.equals(documentoChunkEdit.getId())) { // Evitamos errores e inconsistencias en la lógica de negocio
            throw new EntityIllegalArgumentException("DOCUMENTCHUNK_ID_MISMATCH",
            "El ID proporcionado no coincide con el ID del chunk.");
        }

        DocumentoChunkDB existingEntity = documentoChunkEditRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("DOCUMENTCHUNK_NOT_FOUND_FOR_UPDATE",
                "No se puede actualizar. El chunk con ID " + id + " no existe."));

        DocumentoChunkMapper.INSTANCE.updateDocumentoChunkDBFromDocumentoChunkEdit(documentoChunkEdit, existingEntity);
        return DocumentoChunkMapper.INSTANCE.documentoChunkDBToDocumentoChunkEdit(documentoChunkEditRepository.save(existingEntity));
    }

    @Override
    public List<DocumentoChunkEdit> guardarChunks(List<DocumentoChunkEdit> chunks) {
        List<DocumentoChunkDB> entities = chunks.stream()
            .map(DocumentoChunkMapper.INSTANCE::documentoChunkEditToDocumentoChunkDB)
            .collect(Collectors.toList());

        List<DocumentoChunkDB> savedEntities = documentoChunkEditRepository.saveAll(entities);

        return savedEntities.stream()
            .map(DocumentoChunkMapper.INSTANCE::documentoChunkDBToDocumentoChunkEdit)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (documentoChunkEditRepository.existsById(id)) {
            documentoChunkEditRepository.deleteById(id);
        }
    }

    @Override
    public PaginaResponse<DocumentoChunkList> findAll(List<String> filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @SuppressWarnings("null")
    @Override
    public PaginaResponse<DocumentoChunkList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            // Configurar criterio de filtrado con Specification
            Specification<DocumentoChunkDB> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<DocumentoChunkDB>(
                peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar: puede producir cualquier de los errores controlados en el catch
            Page<DocumentoChunkDB> page = documentoChunkEditRepository.findAll(filtrosBusquedaSpecification, pageable);
            //Devolver respuesta
            return DocumentoChunkMapper.pageToPaginaResponseDocumentoChunkList(
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
