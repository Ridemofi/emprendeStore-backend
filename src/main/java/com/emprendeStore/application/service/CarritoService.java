package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.response.CarritoResponseDto;

public interface CarritoService {

    CarritoResponseDto obtenerCarrito(Long idUsuario);
    CarritoResponseDto agregarProducto(Long idUsuario, Long idProducto);
    CarritoResponseDto actualizarCantidadItem(Long idUsuario, Long idDetalleCarrito, int nuevaCantidad);
    CarritoResponseDto removerItem(Long idUsuario, Long idDetalleCarrito);
}