package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.PedidoService;
import com.emprendeStore.web.dto.request.PedidoRequestDto;
import com.emprendeStore.web.dto.response.PedidoResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedido")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PedidoController {

    private final PedidoService ventaService;

    @PostMapping
    public ResponseEntity<PedidoResponseDto> crearVenta(@Valid @RequestBody PedidoRequestDto request) {
        PedidoResponseDto response = ventaService.crearPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
