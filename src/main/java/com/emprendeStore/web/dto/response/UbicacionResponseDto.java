package com.emprendeStore.web.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UbicacionResponseDto {
    private Long idUbi;
    private String nombreUbi;
    // Este campo será null para Nivel 2 y 3, pero útil para Nivel 1
    private String code;
    private String codigoPostal;
}
