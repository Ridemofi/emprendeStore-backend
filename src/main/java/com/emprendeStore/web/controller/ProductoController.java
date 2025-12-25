package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.ProductoService;
import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.request.UpdateProductoRequestDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService ps;

    @GetMapping("/recientes")
    public ResponseEntity<List<ProductoResponseDTO>> listarRecientes() {
        return ResponseEntity.ok(ps.listarnuevosproductos());
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorFiltroCliente(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) Long emprendedor
    ) {
        String txtFiltro = (q != null && !q.trim().isEmpty()) ? q : null;
        return ResponseEntity.ok(ps.buscarProductos(txtFiltro, categoria, emprendedor));
    }

    @GetMapping("/gestion")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorFiltroGestion(
            @RequestParam Long emprendedor,
            @RequestParam(required = false) String q
    ) {
        String txtFiltro = (q != null && !q.trim().isEmpty()) ? q : null;
        return ResponseEntity.ok(ps.buscarProductosParaGestion(emprendedor, txtFiltro));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> saveProducto(@RequestBody ProductoRequestDTO dto) {
        return new ResponseEntity<>(ps.saveProducto(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> updateProducto(
            @RequestBody UpdateProductoRequestDto dto,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ps.updateProducto(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> deleteProducto(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ps.deleteProducto(id));
    }
}
