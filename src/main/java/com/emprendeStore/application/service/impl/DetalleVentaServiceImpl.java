package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.application.mapper.DetalleVentaMapper;
import com.emprendeStore.application.service.DetalleVentaService;
import com.emprendeStore.domain.model.DetalleCarrito;
import com.emprendeStore.domain.model.DetalleVenta;
import com.emprendeStore.domain.model.Producto;
import com.emprendeStore.domain.model.Venta;
import com.emprendeStore.domain.repository.ProductoRepository;
import com.emprendeStore.web.dto.request.DetalleVentaRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DetalleVentaServiceImpl implements DetalleVentaService {

    private final ProductoRepository proRepo;
    private final DetalleVentaMapper dvm;

    @Override
    @Transactional
    public DetalleVenta crearDetalleVenta(DetalleCarrito itemCarrito, Venta venta) {
        Producto pro = itemCarrito.getProducto();
        if (pro.getStock() < itemCarrito.getCantidad()) {
            throw new ErrorNegocio("Stock insuficiente para el producto: " + pro.getNombreProd());
        }
        pro.setStock(pro.getStock() - itemCarrito.getCantidad());
        pro.recalcularEstado();
        proRepo.save(pro);

        BigDecimal totalItem = itemCarrito.getPrecioUnitario().multiply(BigDecimal.valueOf(itemCarrito.getCantidad()));

        DetalleVentaRequestDto dtoDV = DetalleVentaRequestDto.builder()
                .nombreProducto(pro.getNombreProd())
                .cantidad(itemCarrito.getCantidad())
                .precioUnitario(itemCarrito.getPrecioUnitario())
                .subtotal(totalItem)
                .build();

        return dvm.toEntity(dtoDV, pro, venta);
    }
}
