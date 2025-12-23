package com.emprendeStore.web.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DetalleCarritoResponseDto {
    private Long idDetalle;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private ProductoResponseDTO producto;
}
