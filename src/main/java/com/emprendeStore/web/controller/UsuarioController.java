package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.UsuarioService;
import com.emprendeStore.web.dto.request.RegisterUsuarioRequestDto;
import com.emprendeStore.web.dto.request.UpdateUsuarioRequestDto;
import com.emprendeStore.web.dto.response.UsuarioResponseDto;
import io.swagger.v3.oas.annotations.Operation;
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
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService us;
    @Operation(summary = "Registra un usuario", description = "Registra un usuario nuevo")
    @PostMapping("/saveUsu")
    public ResponseEntity<UsuarioResponseDto> saveUsu(@Valid @RequestBody RegisterUsuarioRequestDto usuario) {
        return new ResponseEntity<>(us.saveUsu(usuario), HttpStatus.CREATED);
    }

    @Operation(summary = "Elimina un usuario", description = "Elimina un usuario por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsu(@PathVariable Long id) {
        us.deleteUsu(id);
        return ResponseEntity.ok("Usuario con ID " + id + " eliminado correctamente");
    }

    @Operation(summary = "Lista todos los usuarios", description = "Lista todos los usuarios registrados")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listarUsus() {
        return ResponseEntity.ok(us.listarUsuarios());
    }

    @Operation(summary = "Actualiza un usuario", description = "Actualiza un usuario por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<String>updateUsu(@Valid @RequestBody UpdateUsuarioRequestDto uDto, @PathVariable Long id){
        us.updateUsu(uDto, id);
        return ResponseEntity.ok("Usuario: " + id + " actualizado correctamente");
    }

    @Operation(summary = "Actualiza la imagen de un usuario", description = "Sube una imagen a Cloudinary y actualiza la URL en el usuario")
    @PostMapping("/{id}/imagen")
    public ResponseEntity<UsuarioResponseDto> actualizarImagenUsuario(@PathVariable Long id, @RequestParam("imagen") MultipartFile imagen) {
        return ResponseEntity.ok(us.saveAndupdateImagenUsuario(id, imagen));
    }

}
