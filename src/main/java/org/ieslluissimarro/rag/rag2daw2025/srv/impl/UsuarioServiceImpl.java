package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.LoginUsuario;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaDto;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioList;
import org.ieslluissimarro.rag.rag2daw2025.repository.UsuarioRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.UsuarioService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.UsuarioMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceImpl implements UsuarioService{
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
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
            UsuarioMapper.INSTANCE.usuariosDbTousuariosList(paginaUsuarioDb.getContent()),
            paginaUsuarioDb.getSort());
    }

    

}
