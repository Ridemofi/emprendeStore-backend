package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.CategoriaService;
import com.emprendeStore.web.dto.request.CategoriaRequestDTO;
import com.emprendeStore.web.dto.response.CategoriaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService cs;

    @PostMapping("/crear")
    public ResponseEntity<CategoriaResponseDTO> crear(@RequestBody CategoriaRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cs.create(requestDTO));
    }

    @GetMapping("/listartodocat")
    public ResponseEntity<List<CategoriaResponseDTO>> listarcategorias(){

        return ResponseEntity.ok(cs.listatodo());
    }

    @GetMapping("/categoriapopulares")
    public ResponseEntity<List<CategoriaResponseDTO>>listarPopulares() {
        return ResponseEntity.ok(cs.categoriaspopular());
    }
}
