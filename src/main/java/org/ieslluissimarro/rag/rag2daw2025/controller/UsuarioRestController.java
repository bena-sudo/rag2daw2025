package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.helper.PaginationHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ListadoRespuesta;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaDto;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioList;
import org.ieslluissimarro.rag.rag2daw2025.srv.UsuarioService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/")
public class UsuarioRestController {
    
    private final UsuarioService usuarioService;


    @GetMapping("/byNickname/{nickname}")
    public ResponseEntity<UsuarioInfo> getUsuarioInfo(@PathVariable String nickname) {
        Optional<UsuarioInfo> usuarioInfo = usuarioService.getByNickName(nickname);
        return usuarioInfo.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }



    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/usuarios")
    public ResponseEntity<ListadoRespuesta<UsuarioList>> getAllUsuarios(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size,
        @RequestParam(defaultValue = "id,asc") String[] sort) {


        Pageable paging = PaginationHelper.createPageable(page, size, sort);

        PaginaDto<UsuarioList> paginaUsuariosList = usuarioService.findAll(paging);

        ListadoRespuesta<UsuarioList> response = new ListadoRespuesta<>(
            paginaUsuariosList.getNumber(),
            paginaUsuariosList.getSize(),
            paginaUsuariosList.getTotalElements(),
            paginaUsuariosList.getTotalPages(),
            paginaUsuariosList.getContent());

            return ResponseEntity.ok(response);
        }





         /**
         * Constructor para inyectar el servicio de Usuarios.
         *
         * @param usuarioService Servicio que maneja la lógica de negocio de los Usuarios.
         */
        public UsuarioRestController(UsuarioService usuarioService) {
            this.usuarioService = usuarioService;
        }
        /**
         * Obtiene una lista paginada de Usuarios con opción de filtrado y ordenación.
         *
         * @param filter Opcional. Filtros de búsqueda en formato `campo:operador:valor` (Ej: "nombre:contiene:Messi").
         * @param page Número de página (por defecto 0).
         * @param size Cantidad de elementos por página (por defecto 3).
         * @param sort Ordenación en formato `campo,direccion` (Ej: "idEquipo,asc").
         * @return Una respuesta con la lista paginada de Usuarios.
         * @throws FiltroException Si ocurre un error en la aplicación de filtros.
         */
        @Operation(summary = "Obtener Usuarios paginados", description = "Retorna una lista paginada de Usuarios aplicando filtros y ordenación opcionales.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de Usuarios obtenida correctamente", 
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class)) }), 
            @ApiResponse(responseCode = "400", description = "Bad Request: Errores de filtrado u ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER'", 
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }), 
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")  })
        @PreAuthorize("hasAuthority('ADMINISTRADOR')")
        @GetMapping("/v1/usuarios")
        public ResponseEntity<PaginaResponse<UsuarioList>> getAllUsuarios(
            @RequestParam(required = false) String[] filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,asc") List<String> sort) throws FiltroException {
            return ResponseEntity.ok(usuarioService.findAll(filter, page, size, sort));
        }

        /**
         * Obtiene una lista paginada de Usuarios mediante una solicitud POST con un objeto de filtros.
         *
         * @param peticionListadoFiltrado Objeto con los filtros de búsqueda y opciones de paginación.
         * @return Una respuesta con la lista paginada de Usuarios.
         * @throws FiltroException Si ocurre un error en la aplicación de filtros.
         */
        @Operation(summary = "Obtener Usuarios con filtros avanzados", description = "Retorna Usuarios paginados enviando los filtros en el cuerpo de la petición.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de Usuarios obtenida correctamente", 
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class)) }), 
            @ApiResponse(responseCode = "400", description = "Bad Request: Errores de filtrado u ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER')", 
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }), 
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @PreAuthorize("hasAuthority('ADMINISTRADOR')")
        @PostMapping("/v1/usuarios")
        public ResponseEntity<PaginaResponse<UsuarioList>> getAllUsuariosPost(
            @Valid @RequestBody PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
            return ResponseEntity.ok(usuarioService.findAll(
                peticionListadoFiltrado));
        }
    }