package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.ProductoService;
import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.request.UpdateProductoRequestDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService ps;

    @Operation(summary = "Listar Productos Nuevos", description = "Listar productos nuevos")
    @GetMapping("/recientes")
    public ResponseEntity<List<ProductoResponseDTO>> listarRecientes() {
        return ResponseEntity.ok(ps.listarnuevosproductos());
    }

    @Operation(summary = "Buscar Productos para Cliente", description = "Buscar productos por filtro")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorFiltroCliente(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) Long emprendedor
    ) {
        String txtFiltro = (q != null && !q.trim().isEmpty()) ? q : null;
        return ResponseEntity.ok(ps.buscarProductos(txtFiltro, categoria, emprendedor));
    }

    @Operation(summary = "Buscar Productos para Gestion", description = "Buscar productos por filtro")
    @GetMapping("/gestion")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorFiltroGestion(
            @RequestParam Long emprendedor,
            @RequestParam(required = false) String q
    ) {
        String txtFiltro = (q != null && !q.trim().isEmpty()) ? q : null;
        return ResponseEntity.ok(ps.buscarProductosParaGestion(emprendedor, txtFiltro));
    }

    @Operation(summary = "Obtener estadísticas de productos", description = "Devuelve el total de productos, el conteo de bajo stock y el valor total del inventario.")
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas(@RequestParam Long emprendedor) {
        return ResponseEntity.ok(ps.obtenerEstadisticas(emprendedor));
    }

    @Operation(summary = "Registrar Producto", description = "Registrar un producto nuevo")
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> saveProducto(@Valid @RequestBody ProductoRequestDTO dto) {
        return new ResponseEntity<>(ps.saveProducto(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar Producto", description = "Actualizar un producto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> updateProducto(
            @Valid @RequestBody UpdateProductoRequestDto dto,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ps.updateProducto(dto, id));
    }

    @Operation(summary = "Eliminar Producto", description = "Eliminar un producto por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> deleteProducto(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ps.deleteProducto(id));
    }
}
