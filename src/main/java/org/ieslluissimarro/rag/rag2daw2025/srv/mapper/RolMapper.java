package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.RolInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface RolMapper {
    RolMapper INSTANCE= Mappers.getMapper(RolMapper.class);


    RolInfo rolDbToRolInfo (RolDb rolDb);
    Set<RolInfo> rolesDbToRolInfo(Set<RolDb> rolesDb);



    default Long rolDbToLong(RolDb rolDb) {
        return rolDb.getId();
    }

    default RolDb longToRolDb(Long id) {
        RolDb rolDb = new RolDb();
        rolDb.setId(id);
        return rolDb;
    }

    default Set<Long> rolDbSetToLongSet(Set<RolDb> roles) {
        return roles.stream().map(this::rolDbToLong).collect(Collectors.toSet());
    }

    default Set<RolDb> longSetToRolDbSet(Set<Long> roleIds) {
        return roleIds.stream().map(this::longToRolDb).collect(Collectors.toSet());
    }
}
