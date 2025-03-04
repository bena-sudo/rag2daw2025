package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.EstadoChunk;
import org.ieslluissimarro.rag.rag2daw2025.srv.DocumentoChunkService;
import org.ieslluissimarro.rag.rag2daw2025.srv.DocumentoServiceDocu;
import org.ieslluissimarro.rag.rag2daw2025.srv.RagService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.DocumentoChunkMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RagServiceImpl implements RagService {

    private final DocumentoServiceDocu documentoService;  
    private final DocumentoChunkService documentoChunkService;  

    @Override
    public List<DocumentoChunkList> subirDoc(Long documentoId) {
        // 1. Verificar si el documento existe
        DocumentoInfo documento = documentoService.read(documentoId);

        // 2. Simular idDocRag (ya que no hay API externa)
        Integer idDocRag = new Random().nextInt(1000) + 1;

        // 3. Generar 10 chunks ficticios
        List<DocumentoChunkEdit> chunks = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            DocumentoChunkEdit chunk = new DocumentoChunkEdit(
                    null, 
                    documentoId,
                    idDocRag,
                    i,  
                    "Texto del chunk " + i,
                    1L, 
                    EstadoChunk.PENDIENTE, 
                    LocalDateTime.now()
            );
            chunks.add(chunk);
        }

        // 4. Guardar los chunks en la BD
        List<DocumentoChunkEdit> chunksGuardados = documentoChunkService.guardarChunks(chunks);

        // 5. Convertir `DocumentoChunkEdit` a `DocumentoChunkList` y retornar
        return chunksGuardados.stream()
        .map(chunk -> new DocumentoChunkList(
                chunk.getId(),                 // ID del chunk
                chunk.getChunkOrder(),         // Orden del chunk
                chunk.getChunkText(),          // Texto del chunk
                chunk.getChunkedBy(),          // Usuario que hizo el chunking
                chunk.getEstado(),             // Estado del chunk
                chunk.getFechaModificacion()   // Fecha de modificación
        ))
        .collect(Collectors.toList());

    }

    @Override
    public DocumentoChunkInfo confirmarChunk(Long chunkId) {
        // Paso 1: Obtener el DocumentoChunkInfo usando el método read
        DocumentoChunkInfo chunkInfo = documentoChunkService.read(chunkId);
        
        // Paso 2: Convertir el DocumentoChunkInfo a DocumentoChunkEdit
        DocumentoChunkEdit chunkEdit = DocumentoChunkMapper.INSTANCE.documentoChunkInfoToDocumentoChunkEdit(chunkInfo);

        // Paso 3: Actualizar el estado en el DocumentoChunkEdit
        chunkEdit.setEstado(EstadoChunk.CHUNKED);

        // Paso 4: Llamar al método update con el DocumentoChunkEdit
        documentoChunkService.update(chunkId, chunkEdit);

        // Paso 5: Devolver el DocumentoChunkInfo actualizado (se vuelve a leer después de actualizar)
        return documentoChunkService.read(chunkId);
    }

}

