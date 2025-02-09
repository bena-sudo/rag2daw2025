package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.LoginUsuario;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaDto;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioList;
import org.springframework.data.domain.Pageable;

import io.micrometer.common.lang.NonNull;

public interface UsuarioService {
    public Optional<UsuarioInfo> getByNickName(@NonNull String nickname);
    public boolean existsByNickname(@NonNull String nickname);
    public boolean existsByEmail(@NonNull String email);
    public boolean comprobarLogin(@NonNull LoginUsuario loginUsuario);

    public PaginaDto<UsuarioList> findAll(Pageable paging);

        /**
         * Busca jugadores aplicando filtros, paginación y ordenación a partir de parámetros individuales.
         * 
         * @param filter Array de strings con los filtros en formato "campo:operador:valor"
         * @param page Número de página (zero-based)
         * @param size Tamaño de cada página
         * @param sort Array con criterios de ordenación en formato "campo", "campo,asc" o "campo,desc"
         * @return PaginaResponse con la lista de jugadores filtrada y paginada
         * @throws FiltroException Si hay errores en los filtros o la ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER')
         */
        public PaginaResponse<UsuarioList> findAll(String[] filter, int page, int size, List<String> sort) throws FiltroException;
        /**
         * Busca jugadores aplicando filtros, paginación y ordenación a partir de una petición estructurada.
         * 
         * @param peticionListadoFiltrado Objeto que encapsula los criterios de búsqueda (nº pagina, tamaño de la página, lista de filtros y lista de ordenaciones)
         * @return PaginaResponse con la lista de jugadores filtrada y paginada
         * @throws FiltroException Si hay errores en los filtros o la ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER')
         */
        public PaginaResponse<UsuarioList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;




        public UsuarioEdit create(UsuarioEdit usuarioEdit);
        public UsuarioEdit read(Long id);
        public UsuarioEdit update(Long id,UsuarioEdit usuarioEdit);
        public void delete(Long id);




}
