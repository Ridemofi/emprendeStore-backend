package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {
    ProductoResponseDTO save(ProductoRequestDTO dto);
    ProductoResponseDTO delete(Long id);
    List<ProductoResponseDTO> listarnuevosproductos();
    List<ProductoResponseDTO> buscarProductos(String texto, Long idCategoria, Long idEmprendedor);
}
