package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.UsuarioService;
import com.emprendeStore.web.dto.request.LoginUsuarioRequestDto;
import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
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

    @PostMapping("/save")
    public ResponseEntity<UsuarioResponseDto> registrarusu(@RequestBody RegisterUsuarioRequestDto usuario) {
        return new ResponseEntity<>(us.save(usuario), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        us.delete(id);
        return ResponseEntity.ok("Usuario con ID " + id + " eliminado correctamente");
    }
    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDto> login(@RequestBody LoginUsuarioRequestDto ldto) {
        return ResponseEntity.ok(us.login(ldto));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar() {
        return ResponseEntity.ok(us.listarUsuarios());
    }
}