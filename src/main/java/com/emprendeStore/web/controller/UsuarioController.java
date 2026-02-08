package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.UsuarioService;
import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
import com.emprendeStore.web.dto.request.UpdateUsuarioRequestDto;
import com.emprendeStore.web.dto.response.UsuarioResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService us;

    @PostMapping("/saveUsu")
    public ResponseEntity<UsuarioResponseDto> saveUsu(@Valid @RequestBody RegisterUsuarioRequestDto usuario) {
        return new ResponseEntity<>(us.saveUsu(usuario), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsu(@PathVariable Long id) {
        us.deleteUsu(id);
        return ResponseEntity.ok("Usuario con ID " + id + " eliminado correctamente");
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listarUsus() {
        return ResponseEntity.ok(us.listarUsuarios());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String>updateUsu(@Valid @RequestBody UpdateUsuarioRequestDto uDto, @PathVariable Long id){
        us.updateUsu(uDto, id);
        return ResponseEntity.ok("Usuario: " + id + " actualizado correctamente");
    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<UsuarioResponseDto> actualizarImagenUsuario(@PathVariable Long id, @RequestParam("imagen") MultipartFile imagen) {
        return ResponseEntity.ok(us.saveAndupdateImagenUsuario(id, imagen));
    }

}
