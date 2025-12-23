package com.emprendeStore.application.mapper.imp;

import com.emprendeStore.application.mapper.CarritoMapper;
import com.emprendeStore.application.mapper.DetalleCarritoMapper;
import com.emprendeStore.domain.model.Carrito;
import com.emprendeStore.domain.model.Usuario;
import com.emprendeStore.web.dto.request.CarritoRequestDTO;
import com.emprendeStore.web.dto.response.CarritoResponseDto;
import com.emprendeStore.web.dto.response.DetalleCarritoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CarritoMapperImpl implements CarritoMapper {
    private final DetalleCarritoMapper dcm;

    @Override
    public Carrito toEntity(CarritoRequestDTO dto, Usuario u) {
        return Carrito.builder()
                .usuario(u)
                .build();
    }

    @Override
    public CarritoResponseDto toDto(Carrito c) {
        if (c == null) {
            return null;
        }

        List<DetalleCarritoResponseDto> detallesDto = (c.getDetalles() != null)
                ? c.getDetalles().stream()
                .map(dcm::toDto)
                .toList()
                : Collections.emptyList(); // Si es nulo, devolvemos una lista vacía

        BigDecimal totalCalculado = detallesDto.stream()
                .map(DetalleCarritoResponseDto::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CarritoResponseDto.builder()
                .idCarrito(c.getIdCarrito())
                .fechaCreacion(c.getFechaCreacion())
                .detalles(detallesDto)
                .total(totalCalculado)
                .build();
    }
}