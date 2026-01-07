package com.emprendeStore.web.dto.request;

import com.emprendeStore.domain.Estados.MetodoPago;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class PedidoRequestDto {
    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsu;
    @NotNull(message = "El ID de dirección es obligatorio")
    private Long idDireccion;
    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;
}
