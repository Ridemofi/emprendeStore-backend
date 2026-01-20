package com.emprendeStore.web.dto.response;

import com.emprendeStore.domain.Estados.EstadoPedido;
import com.emprendeStore.domain.Estados.MetodoPago;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class PedidoResponseDto {
    private Long idPedido;
    private Long idUsuario;
    private String idTransaccion;
    private BigDecimal subtotalGlobal;
    private BigDecimal costoEnvioTotal;
    private BigDecimal totalPagado;
    private MetodoPago metodoPago;
    private EstadoPedido estadoPedido;
    private LocalDateTime fechaPedido;
    private String direccionEnvio;
    private List<VentaResponseDto> ventas;
}
