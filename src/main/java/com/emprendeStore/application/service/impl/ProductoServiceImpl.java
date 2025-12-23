package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.ProductorMapper;
import com.emprendeStore.application.service.ProductoService;
import com.emprendeStore.domain.model.Categoria;
import com.emprendeStore.domain.model.Emprendedor;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.repository.CategoriaRepository;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.domain.repository.ProductoRepository;
import com.emprendeStore.web.dto.request.ProductoRequestDTO;
import com.emprendeStore.web.dto.response.ProductoResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductorMapper pm;
    private final ProductoRepository pr;
    private final CategoriaRepository cr;
    private final EmprendedorRepository er;

    @Override
    @Transactional
    public ProductoResponseDTO save(ProductoRequestDTO dto) {
        Categoria c = cr.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Emprendedor e = er.findById(dto.getIdEmprendedor())
                .orElseThrow(() -> new RuntimeException("Emprendedor no encontrado"));
        Producto p =pm.toEntity(dto, c, e);
        pr.save(p);
        return pm.toDto(p);
    }


    @Override
    public ProductoResponseDTO delete(Long id) {
        Producto p= pr.findById(id).orElseThrow(() -> new ErrorNegocio("El Producto con el id: " + id + " no existe"));
        pr.delete(p);
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
}
