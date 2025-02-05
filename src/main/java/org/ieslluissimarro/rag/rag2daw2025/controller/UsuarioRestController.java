package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioInfo;
import org.ieslluissimarro.rag.rag2daw2025.srv.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/usuarios")
public class UsuarioRestController {
    
    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    // Forma de hacer login de manera no segura
    /*
    @PostMapping("/login")
    public ResponseEntity<String> comprobarLogin(@Valid @RequestBody LoginUsuario loginUsuario) {
        if (usuarioService.comprobarLogin(loginUsuario)) {
            return ResponseEntity.ok("Login válido");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login inválido");
        }
    }
    */

    @GetMapping("/byNickname/{nickname}")
    public ResponseEntity<UsuarioInfo> getUsuarioInfo(@PathVariable String nickname) {
        Optional<UsuarioInfo> usuarioInfo = usuarioService.getByNickName(nickname);
        return usuarioInfo.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    
}
