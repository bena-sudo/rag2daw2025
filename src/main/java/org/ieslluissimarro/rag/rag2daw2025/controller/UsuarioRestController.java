package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.helper.PaginationHelper;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ListadoRespuesta;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaDto;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioList;
import org.ieslluissimarro.rag.rag2daw2025.srv.UsuarioService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/")
public class UsuarioRestController {
    
    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping("/byNickname/{nickname}")
    public ResponseEntity<UsuarioInfo> getUsuarioInfo(@PathVariable String nickname) {
        Optional<UsuarioInfo> usuarioInfo = usuarioService.getByNickName(nickname);
        return usuarioInfo.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }



    @PreAuthorize("hasRole('ADMINISTRADOR')")
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
    
    
}
