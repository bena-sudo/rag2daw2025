package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PermisoDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PermisoList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PermisoMapper {
    PermisoMapper INSTANCE= Mappers.getMapper(PermisoMapper.class);

    List<PermisoDb> permisosListToPermisosDb(List<PermisoList> permisosList);
    List<PermisoList> permisosDbToPermisosList(List<PermisoDb> permisosDb);

}