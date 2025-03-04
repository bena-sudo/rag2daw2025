package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.ieslluissimarro.rag.rag2daw2025.exception.DataValidationException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.filters.specification.FiltroBusquedaSpecification;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PaginationFactory;
import org.ieslluissimarro.rag.rag2daw2025.filters.utils.PeticionListadoFiltradoConverter;
import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.LoginUsuario;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaDto;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioList;
import org.ieslluissimarro.rag.rag2daw2025.repository.RolRepository;
import org.ieslluissimarro.rag.rag2daw2025.repository.UsuarioRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.UsuarioService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.UsuarioMapper;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;
    private final AuditoriaEventoServiceImpl auditoriaEventoService;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository, 
            RolRepository rolRepository,
            PaginationFactory paginationFactory,
            PeticionListadoFiltradoConverter peticionConverter,
            AuditoriaEventoServiceImpl auditoriaEventoService) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.paginationFactory = paginationFactory;
        this.peticionConverter = peticionConverter;
        this.auditoriaEventoService = auditoriaEventoService;
    }

    @Override
    public PaginaResponse<UsuarioList> findAll(List<String> filter, int page, int size, List<String> sort) 
            throws FiltroException {
        /** 'peticionConverter' está en el constructor del service porque utilizando una buena arquitectura
        toda clase externa al Service que contenga un método a ejecutar debería ser testeable de manera
        independiente y para ello debe de estar en el constructor para poderse mockear.**/
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);// En vez de hacer 2 veces el filtrado llamamos al método que lo centraliza
    }

   
    @SuppressWarnings("null")
    @Override
    public PaginaResponse<UsuarioList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        /** 'paginationFactory' está en el constructor del service porque utilizando una buena arquitectura
        toda clase externa al Service que contenga un método a ejecutar debería ser testeable de manera
        independiente y para ello debe de estar en el constructor para poderse mockear.**/
        try {
            // Configurar ordenamiento
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            // Configurar criterio de filtrado con Specification
            Specification<UsuarioDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<UsuarioDb>(
                peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar: puede producir cualquier de los errores controlados en el catch
            Page<UsuarioDb> page = usuarioRepository.findAll(filtrosBusquedaSpecification, pageable);
            //Devolver respuesta
            return UsuarioMapper.pageToPaginaResponse(
                page,
                peticionListadoFiltrado.getListaFiltros(), 
                peticionListadoFiltrado.getSort());
        } catch (JpaSystemException e) {
            String cause="";
            if  (e.getRootCause()!=null){
                if (e.getCause().getMessage()!=null)
                    cause= e.getRootCause().getMessage();
            }
            throw new FiltroException("BAD_OPERATOR_FILTER",
                    "Error: No se puede realizar esa operación sobre el atributo por el tipo de dato", e.getMessage()+":"+cause);
        } catch (PropertyReferenceException e) {
            throw new FiltroException("BAD_ATTRIBUTE_ORDER",
                    "Error: No existe el nombre del atributo de ordenación en la tabla", e.getMessage());
        } catch (InvalidDataAccessApiUsageException e) {
            throw new FiltroException("BAD_ATTRIBUTE_FILTER", "Error: Posiblemente no existe el atributo en la tabla",
                    e.getMessage());
        }
    }






















    @Override
    public Optional<UsuarioInfo> getByNickName(String nickname) {
        Optional<UsuarioDb> usuarioDb = usuarioRepository.findByNickname(nickname);
        if (usuarioDb.isPresent()) {
            return Optional.of(UsuarioMapper.INSTANCE.usuarioDbToUsuarioInfo(usuarioDb.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return usuarioRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public boolean comprobarLogin(LoginUsuario loginUsuario) {
        Optional<UsuarioDb> usuarioDbOpcional = usuarioRepository.findByNickname(loginUsuario.getEmail());
        if (usuarioDbOpcional.isPresent()) {
            UsuarioDb usuarioDb = usuarioDbOpcional.get();
            return usuarioDb.getPassword().equals(loginUsuario.getPassword());
        }
        return false;
    }


    @Override
    public PaginaDto<UsuarioList> findAll(Pageable paging) {
        Page<UsuarioDb> paginaUsuarioDb=usuarioRepository.findAll(paging);
        return new PaginaDto<UsuarioList>(
            paginaUsuarioDb.getNumber(),
            paginaUsuarioDb.getSize(),
            paginaUsuarioDb.getTotalElements(),
            paginaUsuarioDb.getTotalPages(),
            UsuarioMapper.INSTANCE.usuariosDbToUsuariosList(paginaUsuarioDb.getContent()),
            paginaUsuarioDb.getSort());
    }












/*
    @Override
    public UsuarioEdit create(UsuarioEdit usuarioEdit) {
        // if (usuarioRepository.existsById(usuarioEdit.getId())) {
        //     throw new EntityAlreadyExistsException("USER_ALREADY_EXIST",
        //     "El usuario con ID " + usuarioEdit.getId() + " ya existe");
        // }
        if (usuarioEdit.getId() != null) {
            throw new EntityIllegalArgumentException("USER_ID_NOT_ALLOWED",
                "No se puede proporcionar un ID para crear un nuevo usuario.");
            
        }

        UsuarioDb entity = UsuarioMapper.INSTANCE.UsuarioEditToUsuarioDb(usuarioEdit);
entity=usuarioRepository.save(entity);
        // Asignar roles
        if (usuarioEdit.getRoleIds() != null) {
            Set<RolDb> roles = usuarioEdit.getRoleIds().stream()
                .map(roleId -> rolRepository.findById(roleId)
                    .orElseThrow(() -> new EntityNotFoundException("ROLE_NOT_FOUND", "No se encontró el rol con ID " + roleId)))
                .collect(Collectors.toSet());
                entity.setRoles(roles);
        }
entity=usuarioRepository.save(entity);
        return UsuarioMapper.INSTANCE.UsuarioDbToUsuarioEdit(entity);
    }*/

    @Override
    public UsuarioEdit read(Long id) {
        UsuarioDb entity = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("USER_NOT_FOUND", 
                "No se encontró el usuario con ID " + id));
        return UsuarioMapper.INSTANCE.UsuarioDbToUsuarioEdit(entity);
    }

    @Override
    public UsuarioEdit update(Long id, UsuarioEdit usuarioEdit) {
        if (id == null) {
            throw new DataValidationException("ID_NULL", "El ID no puede ser nulo.");
        }
         /*if (!id.equals(usuarioEdit.getId())) {
            throw new EntityIllegalArgumentException("USER_ID_MISMATCH", 
                "El ID proporcionado no coincide con el ID del usuario.");
        }
                */
        UsuarioDb existingEntity = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("USER_NOT_FOUND_FOR_UPDATE", 
                "No se puede actualizar. El usuario con ID " + id + " no existe"));

        usuarioEdit.setId(id);

        String estadoActual = existingEntity.getEstado();
                

        UsuarioMapper.INSTANCE.updateUsuarioDbFromUsuarioEdit(usuarioEdit, existingEntity);

        existingEntity.setEstado(estadoActual);
        // Actualizar roles
        if (usuarioEdit.getRoleIds() != null) {
            Set<RolDb> roles = usuarioEdit.getRoleIds().stream()
                .map(roleId -> rolRepository.findById(roleId)
                    .orElseThrow(() -> new EntityNotFoundException("ROLE_NOT_FOUND", "No se encontró el rol con ID " + roleId)))
                .collect(Collectors.toSet());
            existingEntity.setRoles(roles);
        }

        UsuarioDb updatedEntity = usuarioRepository.save(existingEntity);

        // Registrar evento de auditoría
        auditoriaEventoService.registrarEvento(
            updatedEntity.getId(),
            "modificacion",
            "usuarios",
            existingEntity.getEmail(),
            updatedEntity.getEmail(),
            "Modificación de usuario"
        );
        return UsuarioMapper.INSTANCE.UsuarioDbToUsuarioEdit(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        UsuarioDb usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("USER_NOT_FOUND_FOR_DELETE", 
                "No se puede eliminar. El usuario con ID " + id + " no existe"));
        

        auditoriaEventoService.registrarEvento(
        id,
        "eliminacion",
        "usuarios",
        usuario.getEmail(),
        null,
        "Eliminación de usuario"
    );

    if (usuarioRepository.existsById(id)) {
        usuarioRepository.deleteById(id);
    }
    }




    //Asignar roles a un usuario
    @Override
    public UsuarioDb assignRolesToUsuario(Long usuarioId, List<Long> roleIds) {
        UsuarioDb usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new EntityNotFoundException("USER_NOT_FOUND", "No se encontró el usuario con ID " + usuarioId));

        Set<RolDb> roles = roleIds.stream()
            .map(roleId -> rolRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("ROLE_NOT_FOUND", "No se encontró el rol con ID " + roleId)))
            .collect(Collectors.toSet());

        usuario.setRoles(roles);
        return usuarioRepository.save(usuario);
    }

}
    



