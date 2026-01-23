package com.emprendeStore.application.service.impl;

import com.emprendeStore.application.mapper.VentaMapper;
import com.emprendeStore.application.service.DetalleVentaService;
import com.emprendeStore.application.service.VentaService;
import com.emprendeStore.domain.enums.EstadoVenta;
import com.emprendeStore.domain.model.*;
import com.emprendeStore.domain.repository.EmprendedorRepository;
import com.emprendeStore.domain.repository.VentaRepository;
import com.emprendeStore.web.dto.response.VentaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository vr;
    private final EmprendedorRepository er;
    private final VentaMapper vm;
    private final DetalleVentaService dvs;

    @Override
    @Transactional
    public Venta generarVenta(Pedido pedido, Emprendedor emprendedor, List<DetalleCarrito> items) {
        Venta v = vm.toEntity(pedido, emprendedor, EstadoVenta.PENDIENTE);
        List<DetalleVenta> detallesVenta = new ArrayList<>();
        BigDecimal subtotalVenta = BigDecimal.ZERO;

        for (DetalleCarrito itemCarrito : items) {
            DetalleVenta dv = dvs.crearDetalleVenta(itemCarrito, v);
            detallesVenta.add(dv);
            subtotalVenta = subtotalVenta.add(dv.getSubtotal());
        }

        v.setSubtotal(subtotalVenta);
        v.setTotal(subtotalVenta);
        v.setDetalles(detallesVenta);
        
        emprendedor.setSaldo(emprendedor.getSaldo().add(v.getSubtotal()));
        er.save(emprendedor);

        return vr.save(v);
    }

    @Override
    public List<VentaResponseDto> listarVentasxIdEmprendedor(Long idEmprendedor) {
        return vr.findAllByEmprendedor(idEmprendedor)
                .stream()
                .map(vm::toDto)
                .toList();
    }
}
