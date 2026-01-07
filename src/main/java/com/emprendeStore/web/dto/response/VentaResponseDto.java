package com.emprendeStore.web.dto.response;

import com.emprendeStore.domain.Estados.EstadoVenta;
import com.emprendeStore.domain.Estados.MetodoPago;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class VentaResponseDto {
    private Long idVenta;
    private String idTransaccion;
    private BigDecimal total;
    private MetodoPago metodoPago;
    private EstadoVenta estadoVenta;
    private LocalDateTime fechaVenta;
}