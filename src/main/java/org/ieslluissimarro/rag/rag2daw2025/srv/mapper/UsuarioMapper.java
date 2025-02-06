package org.ieslluissimarro.rag.rag2daw2025.srv.mapper;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;



@Mapper(uses = RolMapper.class)
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(target = "roles", source = "roles")
    UsuarioInfo usuarioDbToUsuarioInfo(UsuarioDb usuarioDb);


    List<UsuarioList> usuariosDbToUsuariosList(List<UsuarioDb> usuariosDb);


    /**
     * Convierte una página de usuarios en una respuesta paginada.
     */
    static PaginaResponse<UsuarioList> pageToPaginaResponse(
            Page<UsuarioDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                UsuarioMapper.INSTANCE.usuariosDbToUsuariosList(page.getContent()),
                filtros,
                ordenaciones
        );
    }
}
