package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.EmprendedorService;
import com.emprendeStore.web.dto.response.EmprendedorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/emprendedor")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EmprendedorController {
    private final EmprendedorService es;

    @GetMapping("/emprepopulares")
    public ResponseEntity<List<EmprendedorResponseDto>> listarPopulares() {

        return ResponseEntity.ok(es.listarEmprePopulares());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmprendedorResponseDto> obtenerEmpreXId(@PathVariable Long id){
        return ResponseEntity.ok(es.obtenerEmpreXId(id));
    }
}
