package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.LoginUsuario;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioInfo;

import io.micrometer.common.lang.NonNull;

public interface UsuarioService {
    public Optional<UsuarioInfo> getByNickName(@NonNull String nickname);
    public boolean existsByNickname(@NonNull String nickname);
    public boolean existsByEmail(@NonNull String email);
    public boolean comprobarLogin(@NonNull LoginUsuario loginUsuario);
}
