package com.emprendeStore.web.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DetalleVentaResponseDto {
    private Long idDetalle;
    private Long idProducto;
    private String nombreProducto;
    private String imagenProducto;
    private Integer cantidad;
    private BigDecimal precioUnit;
    private BigDecimal subtotal;
}
