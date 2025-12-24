package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.UsuarioService;
import com.emprendeStore.web.dto.request.LoginUsuarioRequestDto;
import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
import com.emprendeStore.web.dto.request.UpdateUsuarioRequestDto;
import com.emprendeStore.web.dto.response.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService us;

    @PostMapping("/saveUsu")
    public ResponseEntity<UsuarioResponseDto> saveUsu(@RequestBody RegisterUsuarioRequestDto usuario) {
        return new ResponseEntity<>(us.save(usuario), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        us.delete(id);
        return ResponseEntity.ok("Usuario con ID " + id + " eliminado correctamente");
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar() {
        return ResponseEntity.ok(us.listarUsuarios());
    }
    @PutMapping("/{id}")
    public ResponseEntity<String>updateUsu(@RequestBody UpdateUsuarioRequestDto uDto, @PathVariable Long id){
        us.update(uDto, id);
        return ResponseEntity.ok("Usuario: " + id + " actualizado correctamente");
    }

}