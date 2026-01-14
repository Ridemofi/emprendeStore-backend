package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.response.CarritoResponseDto;

import java.math.BigDecimal;

public interface CarritoService {

    CarritoResponseDto getCarrito(Long idUsuario);
    CarritoResponseDto agregarProducto(Long idUsuario, Long idProducto);
    CarritoResponseDto updateCantidadItem(Long idUsuario, Long idDetalleCarrito, int nuevaCantidad);
    CarritoResponseDto removerItem(Long idUsuario, Long idDetalleCarrito);
    BigDecimal getSubtotalCarritoXUsuario(Long idUsuario);
    void updateSeleccionItem(Long idUsuario, Long idDetalleCarrito, boolean seleccionado);
    void seleccionarTodosLosItems(Long idUsuario, boolean seleccionado);
    BigDecimal calcularCostoEnvioXUsuario(Long idUsuario);
    void limpiarCarrito(Long idUsuario);
    CarritoResponseDto getProductosSeleccionados(Long idUsuario);
}