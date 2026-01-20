package com.emprendeStore.web.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CarritoResponseDto {
    private Long idCarrito;
    private LocalDateTime fechaCreacion;
    private List<DetalleCarritoResponseDto> detalles;
    private BigDecimal total;
}
