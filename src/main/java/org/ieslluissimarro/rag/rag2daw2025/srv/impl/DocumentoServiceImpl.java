package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.filters.specification.FiltroBusquedaSpecification;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PaginationFactory;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PeticionListadoFiltradoConverter;
import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentoDB;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoNew;
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

@Service
@RequiredArgsConstructor
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    @Override
    public DocumentoNew create(DocumentoNew documentoNew) {
        if (documentoNew.getId() != null) {
            throw new EntityIllegalArgumentException("DOCUMENT_ID_MISSMATCH", "El ID debe ser nulo al crear un nuevo documento");
        }
        DocumentoDB entity = DocumentoMapper.INSTANCE.documentoNewToDocumentoDB(documentoNew);
        return DocumentoMapper.INSTANCE.documentoDBToDocumentoNew(documentoRepository.save(entity));
    }

    @Override
    public DocumentoInfo read(Long id) {
        DocumentoDB entity = documentoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("DOCUMENT_ID_MISMATCH", "El documento con ID " + id + " no existe"));
        return DocumentoMapper.INSTANCE.documentoDBToDocumentoInfo(entity);
    }

    @Override
    public DocumentoEdit update(Long id, DocumentoEdit documentoEdit) {
        if (!id.equals(documentoEdit.getId())) {
            throw new EntityIllegalArgumentException("DOCUMENT_ID_MISMATCH", "El ID proporcionado no coincide con el ID del documento");
        }
        DocumentoDB existingEntity = documentoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("DOCUMENT_NOT_FOUND_FOR_UPDATE", "No se puede actualizar. El curso con ID " + id + " no existe"));
        DocumentoMapper.INSTANCE.updateDocumentoDBFromDocumentoEdit(documentoEdit, existingEntity);
        return DocumentoMapper.INSTANCE.documentoDBToDocumentoEdit(documentoRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (documentoRepository.existsById(id)) {
            documentoRepository.deleteById(id);
        }
    }

    @Override
    public PaginaResponse<DocumentoList> findAll(List<String> filter, int page, int size, List<String> sort)
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @SuppressWarnings("null")
    @Override
    public PaginaResponse<DocumentoList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            // Configurar criterio de filtrado con Specification
            Specification<DocumentoDB> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<DocumentoDB>(
                peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar: puede producir cualquier de los errores controlados en el catch
            Page<DocumentoDB> page = documentoRepository.findAll(filtrosBusquedaSpecification, pageable);
            //Devolver respuesta
            return DocumentoMapper.pageToPaginaResponseDocumentoList(
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
