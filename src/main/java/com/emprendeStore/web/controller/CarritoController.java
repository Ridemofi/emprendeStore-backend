package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.CarritoService;
import com.emprendeStore.web.dto.response.CarritoResponseDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService cs;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<CarritoResponseDto> obtenerCarrito(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(cs.getCarrito(idUsuario));
    }

    @PostMapping("/{idUsuario}/item")
    public ResponseEntity<CarritoResponseDto> agregarProducto(@PathVariable Long idUsuario,
                                                              @RequestParam Long idProducto) {
        return ResponseEntity.ok(cs.agregarProducto(idUsuario, idProducto));
    }

    @PutMapping("/{idUsuario}/item/{idDetalleCarrito}")
    public ResponseEntity<CarritoResponseDto> actualizarCantidadItem(@PathVariable Long idUsuario,
                                                                     @PathVariable Long idDetalleCarrito,
                                                                     @RequestParam int nuevaCantidad) {
        return ResponseEntity.ok(cs.updateCantidadItem(idUsuario, idDetalleCarrito, nuevaCantidad));
    }

    @DeleteMapping("/{idUsuario}/item/{idDetalleCarrito}")
    public ResponseEntity<CarritoResponseDto> removerItem(@PathVariable Long idUsuario,
                                                          @PathVariable Long idDetalleCarrito) {
        return ResponseEntity.ok(cs.removerItem(idUsuario, idDetalleCarrito));
    }
    @GetMapping("/{idUsuario}/subtotal")
    public ResponseEntity<BigDecimal> obtenerSubtotalCarrito(@PathVariable Long idUsuario) {
        BigDecimal st = cs.getSubtotalCarritoXUsuario(idUsuario);
        return ResponseEntity.ok(st);
    }

    @PatchMapping("/{idUsuario}/item/{idDetalleCarrito}/seleccion")
    public ResponseEntity<Void> actualizarSeleccionItem(@PathVariable Long idUsuario,
                                                        @PathVariable Long idDetalleCarrito,
                                                        @RequestParam boolean seleccionado) {
        cs.updateSeleccionItem(idUsuario, idDetalleCarrito, seleccionado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idUsuario}/costo-envio")
    public ResponseEntity<BigDecimal> obtenerCostoEnvio(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(cs.calcularCostoEnvio(idUsuario));
    }

    @GetMapping("/{idUsuario}/productos-seleccionados")
    public ResponseEntity<CarritoResponseDto> obtenerProductosSeleccionados(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(cs.getProductosSeleccionados(idUsuario));
    }

}
