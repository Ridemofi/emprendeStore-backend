package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.FavoritoService;
import com.emprendeStore.web.dto.request.FavoritoRequestDto;
import com.emprendeStore.web.dto.response.FavoritoResponseDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/favorito")
public class FavoritoController {
    private final FavoritoService fs;

    @PostMapping("/savefav")
    public ResponseEntity<FavoritoResponseDto> registrarFav(@RequestBody FavoritoRequestDto dto) {
        return ResponseEntity.ok(fs.savefav(dto));
    }

    @GetMapping("/listarfavxusu")
    public ResponseEntity<List<ProductoResponseDTO>> listarFavxUsu(@RequestParam Long id) {
        List<ProductoResponseDTO> lista = fs.listarFavoritosxUsu(id);
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/deletefav")
    public ResponseEntity<Void> deletefav(@RequestParam Long idUsu, @RequestParam Long idPro) {
        fs.deletefav(idUsu, idPro);
        return ResponseEntity.noContent().build();
    }


}
