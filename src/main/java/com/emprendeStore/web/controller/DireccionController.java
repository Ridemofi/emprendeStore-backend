package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.DireccionService;
import com.emprendeStore.web.dto.request.DireccionRequestDto;
import com.emprendeStore.web.dto.response.DireccionResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direccion")
@CrossOrigin("*")
@RequiredArgsConstructor
public class DireccionController {
    private final DireccionService ds;

    @PostMapping("/crear")
    public ResponseEntity<DireccionResponseDto> crearDireccion(@Valid @RequestBody DireccionRequestDto dto) {
        return ResponseEntity.ok(ds.saveDireccion(dto));
    }

    @GetMapping("/listar/{idUsuario}")
    public ResponseEntity<List<DireccionResponseDto>> listarDirecciones(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ds.listarDirecciones(idUsuario));
    }

    @GetMapping("/principal/{idUsuario}")
    public ResponseEntity<DireccionResponseDto> obtenerDireccionPrincipal(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ds.obtenerDireccionPrincipal(idUsuario));
    }

    @PutMapping("/actualizar/{idDireccion}")
    public ResponseEntity<DireccionResponseDto> actualizarDireccion(@PathVariable Long idDireccion, @Valid @RequestBody DireccionRequestDto dto) {
        return ResponseEntity.ok(ds.updateDireccion(idDireccion, dto));
    }

    @DeleteMapping("/eliminar/{idDireccion}")
    public ResponseEntity<Void> eliminarDireccion(@PathVariable Long idDireccion) {
        ds.deleteDireccion(idDireccion);
        return ResponseEntity.noContent().build();
    }
}
