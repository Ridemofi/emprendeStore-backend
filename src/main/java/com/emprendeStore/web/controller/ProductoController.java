package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.ProductoService;
import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService ps;

    @GetMapping("/recientesproductos")
    public ResponseEntity<List<ProductoResponseDTO>> listarRecientes() {
        return ResponseEntity.ok(ps.listarnuevosproductos());
    }
    @GetMapping("/buscarproductosxfiltro")
    public ResponseEntity<List<ProductoResponseDTO>> buscarProxFiltro(@RequestParam(required = false) String q, @RequestParam(required = false) Long categoria,
                                                      @RequestParam(required = false) Long emprendedor){
        String txtFiltro = (q != null && !q.trim().isEmpty()) ? q : null;

        return ResponseEntity.ok(ps.buscarProductos(txtFiltro, categoria, emprendedor));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> registrarProducto(@RequestBody ProductoRequestDTO dto) {
        return new ResponseEntity<>(ps.save(dto), HttpStatus.CREATED);
    }
}