package com.emprendeStore.web.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class PaisResponseDto {
    private Long idPais;
    private String isoCode;
    private String nombrePais;
    private String codigoTelefono;
    private ConfigPaisResponseDto configuracion;
}
