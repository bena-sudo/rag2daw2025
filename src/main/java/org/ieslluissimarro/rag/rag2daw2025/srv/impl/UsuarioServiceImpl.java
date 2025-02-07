package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityAlreadyExistsException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.helper.PaginationFactory;
import org.ieslluissimarro.rag.rag2daw2025.helper.PeticionListadoFiltradoConverter;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.LoginUsuario;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaDto;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioList;
import org.ieslluissimarro.rag.rag2daw2025.repository.UsuarioRepository;
import org.ieslluissimarro.rag.rag2daw2025.specification.FiltroBusquedaSpecification;
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
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;


    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository, 
            PaginationFactory paginationFactory,
            PeticionListadoFiltradoConverter peticionConverter) {
        this.usuarioRepository = usuarioRepository;
        this.paginationFactory = paginationFactory;
        this.peticionConverter = peticionConverter;
    }

    @Override
    public PaginaResponse<UsuarioList> findAll(String[] filter, int page, int size, List<String> sort) 
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













    @Override
    public UsuarioEdit create(UsuarioEdit usuarioEdit) {
        if (usuarioRepository.existsById(usuarioEdit.getId())) {
            throw new EntityAlreadyExistsException("USER_ALREADY_EXIST",
            "El usuario con ID " + usuarioEdit.getId() + " ya existe");
        }
        UsuarioDb entity = UsuarioMapper.INSTANCE.UsuarioEditToUsuarioDb(usuarioEdit);
        return UsuarioMapper.INSTANCE.UsuarioDbToUsuarioEdit(usuarioRepository.save(entity));
    }

    @Override
    public UsuarioEdit read(Long id) {
        UsuarioDb entity = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("USER_NOT_FOUND", 
                "No se encontró el usuario con ID " + id));
        return UsuarioMapper.INSTANCE.UsuarioDbToUsuarioEdit(entity);
    }

    @Override
    public UsuarioEdit update(Long id, UsuarioEdit usuarioEdit) {
        if (!id.equals(usuarioEdit.getId())) {
            throw new EntityIllegalArgumentException("USER_ID_MISMATCH", 
                "El ID proporcionado no coincide con el ID del usuario.");
        }
        UsuarioDb existingEntity = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("USER_NOT_FOUND_FOR_UPDATE", 
                "No se puede actualizar. El usuario con ID " + id + " no existe"));
        
        UsuarioMapper.INSTANCE.updateUsuarioDbFromUsuarioEdit(usuarioEdit, existingEntity);
        return UsuarioMapper.INSTANCE.UsuarioDbToUsuarioEdit(usuarioRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }
    }

}
    



