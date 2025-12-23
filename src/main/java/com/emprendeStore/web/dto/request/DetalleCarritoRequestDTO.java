package com.emprendeStore.web.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DetalleCarritoRequestDTO {
    private Long idCarrito;
    private Long idProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}
