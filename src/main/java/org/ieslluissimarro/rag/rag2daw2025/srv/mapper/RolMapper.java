package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.Set;

import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.RolInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface RolMapper {
    RolMapper INSTANCE= Mappers.getMapper(RolMapper.class);


    RolInfo rolDbToRolInfo (RolDb rolDb);
    Set<RolInfo> rolesDbToRolInfo(Set<RolDb> rolesDb);
}
