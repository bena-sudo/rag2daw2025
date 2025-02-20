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
import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentoDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoEdit;
import org.ieslluissimarro.rag.rag2daw2025.repository.DocumentoRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.DocumentoService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.DocumentoMapper;
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
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public DocumentoEdit create(DocumentoEdit documentoEdit) {
        if (documentoEdit.getId() !=  null) {
            throw new EntityIllegalArgumentException("DOCUMENTO_ID_MISMATCH", "El ID debe ser nulo al crear un nuevo documento");
        }
        DocumentoDb entity = DocumentoMapper.INSTANCE.documentoEditToDocumentoDb(documentoEdit);
        return DocumentoMapper.INSTANCE.documentoDbToDocumentoEdit(documentoRepository.save(entity));
    }

    @Override
    public DocumentoEdit read(long id) {
        DocumentoDb entity = documentoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("DOC_NOT_FOUND", "No se encontró el documento con id: " + id));
        return DocumentoMapper.INSTANCE.documentoDbToDocumentoEdit(entity);
    }

    @Override
    public DocumentoEdit update(long id, DocumentoEdit documentoEdit) {

        if (id != (documentoEdit.getId())) {
            throw new EntityIllegalArgumentException("DOCUMENTO_ID_MISMATCH", "El ID proporcionado no coincide con el ID del documento a actualizar.");
        }

        DocumentoDb existingEntity = documentoRepository.findById(id)
                                                           .orElseThrow(() -> new EntityNotFoundException("DOCUMENTO_NOT_FOUND", "Documento no encontrado con id " + documentoEdit.getId()));
        
        DocumentoMapper.INSTANCE.updateDocumentoDbFromDocumentoEdit(documentoEdit, existingEntity);
        return DocumentoMapper.INSTANCE.documentoDbToDocumentoEdit(documentoRepository.save(existingEntity));
    }

    @Override
    public void delete(long id) {
        documentoRepository.deleteById(id);
    }

    @Override
    public PaginaResponse<DocumentoEdit> findAll(List<String> filter, int page, int size, List<String> sort) 
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @Override
    public PaginaResponse<DocumentoEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            Specification<DocumentoDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(
                peticionListadoFiltrado.getListaFiltros());
            Page<DocumentoDb> page = documentoRepository.findAll(filtrosBusquedaSpecification, pageable);
            return DocumentoMapper.pageToPaginaResponseDocumentoEdit(
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