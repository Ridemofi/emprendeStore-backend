package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.ProductorMapper;
import com.emprendeStore.application.service.ProductoService;
import com.emprendeStore.domain.Estados.EstadoProducto;
import com.emprendeStore.domain.model.Categoria;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.repository.CategoriaRepository;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.domain.repository.ProductoRepository;
import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.request.UpdateProductoRequestDto;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductorMapper pm;
    private final ProductoRepository pr;
    private final CategoriaRepository cr;
    private final EmprendedorRepository er;
    private final CloudinaryServiceImpl cs;

    @Override
    @Transactional
    public ProductoResponseDTO saveProducto(ProductoRequestDTO dto, MultipartFile imgpro) {
        Categoria c = cr.findById(dto.getIdCategoria()).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Emprendedor e = er.findById(dto.getIdEmprendedor()).orElseThrow(() -> new RuntimeException("Emprendedor no encontrado"));
        if(imgpro!=null && !imgpro.isEmpty()){
            String urlimg = cs.uploadProductoImage(imgpro);
            dto.setImgpro(urlimg);
        }
        Producto p = pm.toEntity(dto, c, e);
        p.recalcularEstado();
        pr.save(p);
        return pm.toDto(p);
    }

    @Transactional
    @Override
    public ProductoResponseDTO deleteProducto(Long id) {
        Producto p = pr.findById(id).orElseThrow(() -> new ErrorNegocio("El Producto con el id: " + id + " no existe"));
        if(p.getImgPro()!=null && !p.getImgPro().isEmpty()){
            cs.deleteImage(p.getImgPro());
        }
        pr.delete(p);
        return pm.toDto(p);
    }

    @Transactional
    @Override
    public ProductoResponseDTO updateProducto(UpdateProductoRequestDto dto, Long id) {
        Producto p = pr.findById(id).orElseThrow(() -> new ErrorNegocio("Producto no encontrado"));
        pm.updateEntity(dto, p);
        if (dto.getIdCategoria() != null) {
            Categoria c = cr.findById(dto.getIdCategoria()).orElseThrow(() -> new ErrorNegocio("Categoría no encontrada"));
            p.setCategoria(c);
        }
        if (StringUtils.hasText(dto.getEstado())) {
            p.cambiarEstado(EstadoProducto.obtenerDesde(dto.getEstado()));
        }
        // recalcularEstado decide Disponible/Bajo/Agotado según stock,
        // excepto cuando el producto está Pausado.
        p.recalcularEstado();
        return pm.toDto(p);
    }

    @Override
    public List<ProductoResponseDTO> listarnuevosproductos() {
        return pr.findRecienLlegados()
                .stream()
                .map(pm::toDto)
                .toList();
    }

    @Override
    public List<ProductoResponseDTO> buscarProductos(String texto, Long idCategoria, Long idEmprendedor) {
        return pr.buscarProConFiltros(texto, idCategoria, idEmprendedor)
                .stream()
                .map(pm::toDto)
                .toList();
    }

    @Override
    public List<ProductoResponseDTO> buscarProductosParaGestion(Long idEmprendedor, String texto) {
        return pr.buscarProductParaGestion(idEmprendedor, texto)
                .stream()
                .map(pm::toDto)
                .toList();
    }

    @Override
    public Map<String, Object> obtenerEstadisticas(Long idEmprendedor) {
        long totalProductos = pr.countByEmprendedorIdempre(idEmprendedor);
        long productosBajoStock = pr.countByEmprendedorIdempreAndEstadoProducto(idEmprendedor, EstadoProducto.Bajo);
        BigDecimal valorInventario = pr.calcularValorTotalInventarioByEmprendedor(idEmprendedor);

        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("totalProductos", totalProductos);
        estadisticas.put("productosBajoStock", productosBajoStock);
        estadisticas.put("valorTotalInventario", valorInventario);

        return estadisticas;
    }

    @Override
    @Transactional
    public ProductoResponseDTO updateImagenProducto(Long idProducto, MultipartFile imgpro) {
        Producto p= pr.findById(idProducto).orElseThrow(() -> new ErrorNegocio("Producto no encontrado"));
        if(p.getImgPro()!=null && !p.getImgPro().isEmpty()){
            cs.deleteImage(p.getImgPro());
        }
        String urlimg = cs.uploadProductoImage(imgpro);
        p.setImgPro(urlimg);
        return pm.toDto(p);
    }
}
