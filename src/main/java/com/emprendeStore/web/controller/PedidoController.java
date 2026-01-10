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
public class PedidoController {

    private final PedidoService ps;

    @PostMapping
    public ResponseEntity<PedidoResponseDto> crearVenta(@Valid @RequestBody PedidoRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ps.crearPedido(request));
    }

}
