package com.emprendeStore.web.dto.response;

import com.emprendeStore.domain.enums.EstadoVenta;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class VentaResponseDto {
    private Long idVenta;
    private Long idPedido;
    private Long idEmpre;
    private String nombreEmprendedor;
    private BigDecimal subTotal;
    private BigDecimal total;
    private EstadoVenta estadoVenta;
    private List<DetalleVentaResponseDto> detalles;
}
