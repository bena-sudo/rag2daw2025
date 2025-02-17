package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.AuditoriaEventoDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.AuditoriaEventoList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuditoriaMapper {
    AuditoriaMapper INSTANCE= Mappers.getMapper(AuditoriaMapper.class);

    AuditoriaEventoDb auditoriaDbToAuditoriaList (AuditoriaEventoList auditoriaList);
    AuditoriaEventoList auditoriaListToAuditoriaDb(AuditoriaEventoDb auditoriaDb);

    List<AuditoriaEventoDb> auditoriasListToAuditoriasDb(List<AuditoriaEventoList> auditoriasList);
    List<AuditoriaEventoList> auditoriasDbToAuditoriasList(List<AuditoriaEventoDb> auditoriasDb);

    
}
