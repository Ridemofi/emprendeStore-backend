package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.request.UpdateProductoRequestDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductoService {
    ProductoResponseDTO saveProducto(ProductoRequestDTO dto, MultipartFile imgpro);
    ProductoResponseDTO deleteProducto(Long id);
    ProductoResponseDTO updateProducto(UpdateProductoRequestDto dto, Long id);
    List<ProductoResponseDTO> listarnuevosproductos();
    List<ProductoResponseDTO> buscarProductos(String texto, Long idCategoria, Long idEmprendedor);
    List<ProductoResponseDTO> buscarProductosParaGestion(Long idEmprendedor, String texto);
    Map<String, Object> obtenerEstadisticas(Long idEmprendedor);
    ProductoResponseDTO updateImagenProducto(Long idProducto, MultipartFile imgpro);
    List<ProductoResponseDTO> listarRecomendacionesCarrito(Long idUsuario);
}
