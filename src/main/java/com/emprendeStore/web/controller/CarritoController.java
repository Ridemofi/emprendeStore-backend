package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.CarritoService;
import com.emprendeStore.web.dto.response.CarritoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/carrito")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService cs;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<CarritoResponseDto> obtenerCarrito(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(cs.obtenerCarrito(idUsuario));
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
        return ResponseEntity.ok(cs.actualizarCantidadItem(idUsuario, idDetalleCarrito, nuevaCantidad));
    }

    @DeleteMapping("/{idUsuario}/item/{idDetalleCarrito}")
    public ResponseEntity<CarritoResponseDto> removerItem(@PathVariable Long idUsuario,
                                                          @PathVariable Long idDetalleCarrito) {
        return ResponseEntity.ok(cs.removerItem(idUsuario, idDetalleCarrito));
    }


}
