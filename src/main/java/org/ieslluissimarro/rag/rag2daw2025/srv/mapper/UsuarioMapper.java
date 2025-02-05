package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;



@Mapper(uses = RolMapper.class)
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(target = "roles", source = "roles")
    UsuarioInfo usuarioDbToUsuarioInfo(UsuarioDb usuarioDb);
}
