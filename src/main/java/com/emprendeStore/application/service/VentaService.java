package com.emprendeStore.application.service;

import com.emprendeStore.domain.model.DetalleCarrito;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Pedido;
import com.emprendeStore.domain.model.Venta;
import com.emprendeStore.web.dto.response.VentaResponseDto;

import java.util.List;

public interface VentaService {
    Venta generarVenta(Pedido pedido, Emprendedor emprendedor, List<DetalleCarrito> items);
    List<VentaResponseDto> listarVentasxIdEmprendedor(Long idEmprendedor);
}
