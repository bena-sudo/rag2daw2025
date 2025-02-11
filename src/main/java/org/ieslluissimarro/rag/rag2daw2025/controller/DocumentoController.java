package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoNew;
import org.ieslluissimarro.rag.rag2daw2025.srv.DocumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DocumentoController {

    private final DocumentoService documentoService;

    @PostMapping(value = "/documento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentoNew> create(
            @RequestPart("file") MultipartFile file, 
            @RequestPart("documento") DocumentoNew documentoNew, 
            BindingResult bindingResult) {

        // Validación de los campos
        BindingResultHelper.validateBindingResult(bindingResult, "DOCUMENT_CREATE_VALIDATION");

        // Asignar el archivo al objeto
        documentoNew.setMultipart(file);
        documentoNew.setContentTypeDocumento(file.getContentType());
        documentoNew.setExtensionDocumento(getFileExtension(file.getOriginalFilename()));

        // Guardar en la base de datos
        DocumentoNew createdDocumento = documentoService.create(documentoNew);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDocumento);
    }

    // Método para obtener la extensión del archivo
    private String getFileExtension(String filename) {
        return filename != null && filename.contains(".") ? filename.substring(filename.lastIndexOf(".") + 1) : "";
    }

    @GetMapping("/documento/{id}")
    public ResponseEntity<DocumentoInfo> read(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.read(id));
    }
    
    @PutMapping("/documento/{id}")
    public ResponseEntity<DocumentoEdit> update(@PathVariable Long id, @Valid @RequestBody DocumentoEdit documentoEdit, BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "DOCUMENT_UPDATE_VALIDATION");
        return ResponseEntity.ok(documentoService.update(id, documentoEdit));
    }
    
    @DeleteMapping("/documento/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/documentos")
    public ResponseEntity<PaginaResponse<DocumentoList>> getAllDocumentosGET(
                    @RequestParam(required = false) List<String> filter,
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "3") int size,
                    @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
            return ResponseEntity.ok(documentoService.findAll(filter, page, size, sort));
    }

    @PostMapping("/documentos")
    public ResponseEntity<PaginaResponse<DocumentoList>> getAllDocumentosPOST(
                    @Valid @RequestBody PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
            return ResponseEntity.ok(documentoService.findAll(peticionListadoFiltrado));
    }

}
