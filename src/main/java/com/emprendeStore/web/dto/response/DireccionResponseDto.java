package com.emprendeStore.web.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DireccionResponseDto {
    private Long idDireccion;
    private String nombreContacto;
    private String telefono;
    private PaisResponseDto pais;
    private String ubicacionNivel1;
    private String ubicacionNivel2;
    private String ubicacionNivel3;
    private String direccion1;
    private String direccion2;
    private String codigoPostal;
    private boolean esPrincipal;
}
