package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.ProductoService;
import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.request.UpdateProductoRequestDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorFiltroGestion(@RequestParam Long emprendedor, @RequestParam(required = false) String q) {
        String txtFiltro = (q != null && !q.trim().isEmpty()) ? q : null;
        return ResponseEntity.ok(ps.buscarProductosParaGestion(emprendedor, txtFiltro));
    }

    @Operation(summary = "Obtener estadísticas de productos", description = "Devuelve el total de productos, el conteo de bajo stock y el valor total del inventario.")
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas(@RequestParam Long emprendedor) {
        return ResponseEntity.ok(ps.obtenerEstadisticas(emprendedor));
    }

    @Operation(summary = "Registrar Producto con Imagen", description = "Registra un producto y sube su imagen")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductoResponseDTO> saveProducto(@RequestPart("datos") @Valid ProductoRequestDTO dto,
            @RequestPart(value = "imgpro", required = false) MultipartFile imgpro) {
        // Pasamos ambos al servicio
        return new ResponseEntity<>(ps.saveProducto(dto, imgpro), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar Producto", description = "Actualizar un producto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> updateProducto(@Valid @RequestBody UpdateProductoRequestDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(ps.updateProducto(dto, id));
    }

    @Operation(summary = "Eliminar Producto", description = "Eliminar un producto por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> deleteProducto(@PathVariable Long id) {
        return ResponseEntity.ok(ps.deleteProducto(id));
    }

    @Operation(summary = "Actualizar Imagen de Producto", description = "Sube una imagen a Cloudinary y actualiza la URL en el producto")
    @PostMapping("/{id}/imagen")
    public ResponseEntity<ProductoResponseDTO> actualizarImagenProducto(@PathVariable Long id, @RequestParam("imgpro") MultipartFile imgpro) {
        return ResponseEntity.ok(ps.updateImagenProducto(id, imgpro));
    }

    @Operation(summary = "Obtener Recomendaciones", description = "Obtiene productos recomendados basados en el carrito del usuario")
    @GetMapping("/recomendaciones/{idUsuario}")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerRecomendaciones(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ps.listarRecomendacionesCarrito(idUsuario));
    }
}
