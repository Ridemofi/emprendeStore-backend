package com.emprendeStore.application.service;

import com.emprendeStore.domain.model.DetalleCarrito;
import com.emprendeStore.domain.model.DetalleVenta;
import com.emprendeStore.domain.model.Venta;

public interface DetalleVentaService {
    DetalleVenta crearDetalleVenta(DetalleCarrito itemCarrito, Venta venta);
}
