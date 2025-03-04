package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.AuditoriaEventoDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.AuditoriaEventoList;
import org.ieslluissimarro.rag.rag2daw2025.repository.AuditoriaEventoRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.AuditoriaEventoService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.AuditoriaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaEventoServiceImpl implements AuditoriaEventoService{

    @Autowired
    private AuditoriaEventoRepository auditoriaEventoRepository;

    public void registrarEvento(Long usuarioId, String tipoEvento, String tablaAfectada, String datoAnterior, String datoNuevo, String descripcion) {
        AuditoriaEventoDb evento = new AuditoriaEventoDb();
        evento.setUsuarioId(usuarioId);
        evento.setTipoEvento(tipoEvento);
        evento.setTablaAfectada(tablaAfectada);
        evento.setDatoAnterior(datoAnterior);
        evento.setDatoNuevo(datoNuevo);
        evento.setDescripcion(descripcion);
        evento.setFecha(LocalDateTime.now());

        auditoriaEventoRepository.save(evento);
    }

    @Override
    public List<AuditoriaEventoList> findByUsuarioId(Long usuarioId) {
        
        return AuditoriaMapper.INSTANCE.auditoriasDbToAuditoriasList(auditoriaEventoRepository.findByUsuarioId(usuarioId));
    }

  
    
}
