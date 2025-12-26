package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.request.UpdateProductoRequestDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductoService {
    ProductoResponseDTO saveProducto(ProductoRequestDTO dto);
    ProductoResponseDTO deleteProducto(Long id);
    ProductoResponseDTO updateProducto(UpdateProductoRequestDto dto, Long id);
    List<ProductoResponseDTO> listarnuevosproductos();
    List<ProductoResponseDTO> buscarProductos(String texto, Long idCategoria, Long idEmprendedor);
    List<ProductoResponseDTO> buscarProductosParaGestion(Long idEmprendedor, String texto);

    /**
     * Obtiene las estadísticas de productos para un emprendedor.
     * @param idEmprendedor El ID del emprendedor.
     * @return Un mapa con las estadísticas.
     */
    Map<String, Object> obtenerEstadisticas(Long idEmprendedor);
}
