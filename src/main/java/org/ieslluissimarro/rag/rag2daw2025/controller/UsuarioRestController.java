package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.BindingResultErrorsResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.BindingResultException;
import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.DataValidationException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.helper.BindingResultHelper;
import org.ieslluissimarro.rag.rag2daw2025.helper.PaginationHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.IdEntityLong;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ListadoRespuesta;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaDto;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioList;
import org.ieslluissimarro.rag.rag2daw2025.srv.AuditoriaEventoService;
import org.ieslluissimarro.rag.rag2daw2025.srv.UsuarioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/")
public class UsuarioRestController {

        private final UsuarioService usuarioService;

        /**
         * Obtiene una lista paginada de Usuarios.
         *
         * @param page Número de página.
         * @param size Cantidad de elementos por página.
         * @param sort Ordenación en formato `campo,direccion` (Ej: "id,asc").
         * @return Una respuesta con la lista paginada de Usuarios.
         */
        @Operation(summary = "Obtener lista paginada de usuarios", description = "Devuelve una lista paginada de usuarios con ordenación y filtros opcionales.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class)) }),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
        })
        @PreAuthorize("@authorizationService.hasPermission('VER_USUARIOS')")
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
         * @param usuarioService Servicio que maneja la lógica de negocio de los
         *                       Usuarios.
         */
        public UsuarioRestController(UsuarioService usuarioService, AuditoriaEventoService auditoriaEventoService) {
                this.usuarioService = usuarioService;

        }

        /**
         * Obtiene una lista paginada de Usuarios con opción de filtrado y ordenación.
         *
         * @param filter Opcional. Filtros de búsqueda en formato `campo:operador:valor`
         *               (Ej: "nombre:contiene:Messi").
         * @param page   Número de página (por defecto 0).
         * @param size   Cantidad de elementos por página (por defecto 3).
         * @param sort   Ordenación en formato `campo,direccion` (Ej: "idEquipo,asc").
         * @return Una respuesta con la lista paginada de Usuarios.
         * @throws FiltroException Si ocurre un error en la aplicación de filtros.
         */
        @Operation(summary = "Obtener Usuarios paginados", description = "Retorna una lista paginada de Usuarios aplicando filtros y ordenación opcionales.")
        @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de Usuarios obtenida correctamente", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de filtrado u ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER'", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor") })
        @PreAuthorize("hasAuthority('ADMINISTRADOR')")
        @GetMapping("/v1/usuarios")
        public ResponseEntity<PaginaResponse<UsuarioList>> getAllUsuarios(
                        @RequestParam(required = false) List<String> filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size,
                        @RequestParam(defaultValue = "id,asc") List<String> sort) throws FiltroException {
                return ResponseEntity.ok(usuarioService.findAll(filter, page, size, sort));
        }

        /**
         * Obtiene una lista paginada de Usuarios mediante una solicitud POST con un
         * objeto de filtros.
         *
         * @param peticionListadoFiltrado Objeto con los filtros de búsqueda y opciones
         *                                de paginación.
         * @return Una respuesta con la lista paginada de Usuarios.
         * @throws FiltroException Si ocurre un error en la aplicación de filtros.
         */
        @Operation(summary = "Obtener Usuarios con filtros avanzados", description = "Retorna Usuarios paginados enviando los filtros en el cuerpo de la petición.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lista de Usuarios obtenida correctamente", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaResponse.class)) }),
                        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de filtrado u ordenación (errorCodes: 'BAD_OPERATOR_FILTER','BAD_ATTRIBUTE_ORDER','BAD_ATTRIBUTE_FILTER','BAD_FILTER')", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @PreAuthorize("@authorizationService.hasPermission('VER_USUARIOS')")
        @PostMapping("/v1/usuarios/x")
        public ResponseEntity<PaginaResponse<UsuarioList>> getAllUsuariosPost(
                        @Valid @RequestBody PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
                return ResponseEntity.ok(usuarioService.findAll(
                                peticionListadoFiltrado));
        }

        /******************* CRUD *********************/

        // /**
        // * Crea un nuevo usuario en el sistema.
        // *
        // * @param usuarioEdit Objeto que contiene los datos del usuario a crear. Debe
        // * cumplir con las validaciones definidas.
        // * @param bindingResult Resultado de las validaciones de los datos
        // * proporcionados.
        // * @return Un objeto usuarioEdit con los datos creados, junto con un código de
        // * estado HTTP 201 (CREATED).
        // * @throws HttpMessageNotReadableExceptiom Si no coincide el tipo de dato de
        // * algún atributo y no se puede pasear.
        // * @throws BindingResultException Si hay errores de validación en el
        // * objeto usuarioEdit.
        // * @throws EntityAlreadyExistsException Si ya existe un usuario con el ID
        // * especificado.
        // * @throws DataIntegrityViolationException Si hay errores al almacenar el
        // * registro en la BD (clave ajena,
        // * restricción de unicidad, integridad
        // * de datos).
        // */
        // @Operation(summary = "Crea un nuevo registro de tipo usuario en el sistema.")
        // @ApiResponses(value = {
        // @ApiResponse(responseCode = "201", description = "Created: 'usuario' creado
        // exitosamente",
        // content = {@Content(mediaType = "application/json", schema =
        // @Schema(implementation = UsuarioEdit.class)) }),
        // @ApiResponse(responseCode = "400", description = "Bad Request: Errores de
        // validación en los datos proporcionados (errorCode='USER_CREATE_VALIDATION',
        // 'DATA CONVERSION_ERROR')",
        // content = { @Content(mediaType = "application/json", schema =
        // @Schema(implementation = BindingResultErrorsResponse.class)) }),
        // @ApiResponse(responseCode = "409", description = "Conflict: Error al intentar
        // crear un 'usuario' (errorCodes: 'USER_ALREADY_EXIST',
        // 'FOREIGN_KEY_VIOLATION', 'UNIQUE_CONSTRAINT_VIOLATION',
        // 'DATA_INTEGRITY_VIOLATION')",
        // content = {@Content(mediaType = "application/json", schema =
        // @Schema(implementation = CustomErrorResponse.class)) })
        // })
        // @PostMapping("/v1/usuarios")
        // public ResponseEntity<UsuarioEdit> create(@Valid @RequestBody UsuarioEdit
        // usuarioEdit, BindingResult bindingResult) {
        // // Comprueba errores de validación y si los hay lanza una
        // BindingResultException
        // // con el errorCode
        // BindingResultHelper.validateBindingResult(bindingResult,
        // "USER_CREATE_VALIDATION");
        // // No hay error de validación y procedemos a crear el nuevo registro
        // return
        // ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(usuarioEdit));
        // }

        /**
         * Obtiene los datos de un usuario a partir de su ID.
         *
         * @param id ID del usuario a buscar.
         * @return Un objeto usuarioEdit con los datos del usuario, junto con un código
         *         de
         *         estado HTTP 200 (OK).
         * @throws EntityNotFoundException Si no se encuentra un usuario con el ID
         *                                 especificado.
         * @throws DataValidationException Si el formato del ID es incorrecto.
         */
        @Operation(summary = "Devuelve los datos de un usuario dado su ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK: usuario encontrado con éxito", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioEdit.class)) }),
                        @ApiResponse(responseCode = "400", description = "Bad Request: Error de validación en el ID proporcionado (errorCode='ID_FORMAT_INVALID')", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
                        @ApiResponse(responseCode = "404", description = "Not Found: No se encontró el usuario con el ID proporcionado (errorCode='USER_NOT_FOUND')", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
        })
        @PreAuthorize("@authorizationService.hasPermission('VER_USUARIO')")
        @GetMapping("/v1/usuarios/{id}")
        public ResponseEntity<UsuarioEdit> read(@PathVariable String id) {
                return ResponseEntity.ok(usuarioService.read(new IdEntityLong(id).getValue()));
        }

        /**
         * Actualiza los datos de un usuario existente.
         *
         * @param id            ID del usuario a actualizar.
         * @param usuarioEdit   Objeto que contiene los datos actualizados del usuario.
         * @param bindingResult Resultado de las validaciones de los datos
         *                      proporcionados.
         * @return Un objeto usuarioEdit con los datos actualizados, junto con un código
         *         de estado HTTP 200 (OK).
         * @throws HttpMessageNotReadableExceptiom Si no coincide el tipo de dato de
         *                                         algún atributo y no se puede pasear.
         * @throws BindingResultException          Si hay errores de validación en el
         *                                         objeto usuarioEdit.
         * @throws DataValidationException         Si el formato del ID es incorrecto.
         * @throws EntityIllegalArgumentException  Si el ID especificado no coincide
         *                                         con el del usuario a actualizar.
         * @throws EntityNotFoundException         Si no se encuentra un usuario con el
         *                                         ID especificado.
         * @throws DataIntegrityViolationException Si hay errores al almacenar el
         *                                         registro en la BD (clave ajena,
         *                                         restricción de unicidad, integridad
         *                                         de datos).
         */
        @Operation(summary = "Actualiza los datos de un usuario existente en el sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK: usuario actualizado con éxito", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioEdit.class)) }),
                        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de validación en los datos proporcionados (errorCode='USER_UPDATE_VALIDATION')", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = BindingResultErrorsResponse.class)) }),
                        @ApiResponse(responseCode = "400", description = "Bad Request: Errores de validación en el ID proporcionado (errorCodes='DATA CONVERSION_ERROR','ID_FORMAT_INVALID','USER_ID_MISMATCH')", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
                        @ApiResponse(responseCode = "404", description = "Not Found: No se encontró el usuario con el ID proporcionado (errorCode='USER_NOT_FOUND_FOR_UPDATE')", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
                        @ApiResponse(responseCode = "409", description = "Conflict: Error al intentar actualizar un 'usuario' (errorCodes: 'FOREIGN_KEY_VIOLATION', 'UNIQUE_CONSTRAINT_VIOLATION', 'DATA_INTEGRITY_VIOLATION')", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
        })
        @PreAuthorize("@authorizationService.hasPermission('EDITAR_USUARIO')")
        @PutMapping("/v1/usuarios/{id}")
        public ResponseEntity<UsuarioEdit> update(@PathVariable Long id, @Valid @RequestBody UsuarioEdit usuarioEdit,
                        BindingResult bindingResult) {
                // Comprueba errores de validación y si los hay lanza una BindingResultException
                // con el errorCode
                BindingResultHelper.validateBindingResult(bindingResult, "USER_UPDATE_VALIDATION");
                // No hay error de validación y procedemos a modificar el registro
                return ResponseEntity.ok(usuarioService.update(id, usuarioEdit));
        }

        /**
         * Elimina un usuario del sistema.
         *
         * @param ID ID del usuario a eliminar.
         * @return Un código de estado HTTP 204 (NO CONTENT) si la operación es exitosa.
         * @throws DataValidationException Si el formato del ID es incorrecto.
         */
        @Operation(summary = "Elimina un registro de usuario del sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "No Content: usuario eliminado", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad Request: Error de validación en el ID proporcionado (errorCode='ID_FORMAT_INVALID')", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
        })
        @PreAuthorize("@authorizationService.hasPermission('ELIMINAR_USUARIO')")
        @DeleteMapping("/v1/usuarios/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
                usuarioService.delete(id);
                return ResponseEntity.noContent().build();
        }

        /**
         * Asigna roles a un usuario.
         */
        @Operation(summary = "Asigna roles a un usuario.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Roles asignados correctamente"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
        })
        @PostMapping("/v1/usuarios/{usuarioId}/roles")
        public ResponseEntity<UsuarioDb> assignRolesToUsuario(@PathVariable Long usuarioId,
                        @RequestBody List<Long> roleIds) {
                UsuarioDb usuario = usuarioService.assignRolesToUsuario(usuarioId, roleIds);
                return ResponseEntity.ok(usuario);
        }

}