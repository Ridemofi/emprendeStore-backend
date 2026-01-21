package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.VentaService;
import com.emprendeStore.web.dto.response.VentaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/venta")
@RequiredArgsConstructor
public class VentaController {
    private final VentaService vs;

    @GetMapping("/emprendedor/{idEmprendedor}")
    public ResponseEntity<List<VentaResponseDto>> listarVentasxEmprendedor(@PathVariable Long idEmprendedor){
        return ResponseEntity.ok(vs.listarVentasxIdEmprendedor(idEmprendedor));
    }

}
