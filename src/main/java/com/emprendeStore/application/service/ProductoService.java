package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.request.UpdateProductoRequestDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {
    ProductoResponseDTO saveProducto(ProductoRequestDTO dto);
    ProductoResponseDTO deleteProducto(Long id);
    ProductoResponseDTO updateProducto(UpdateProductoRequestDto dto, Long id);
    List<ProductoResponseDTO> listarnuevosproductos();
    List<ProductoResponseDTO> buscarProductos(String texto, Long idCategoria, Long idEmprendedor);
    List<ProductoResponseDTO> buscarProductosParaGestion(Long idEmprendedor, String texto);
}
