package com.emprendeStore.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DireccionRequestDto {
    @NotNull(message = "El usuario es obligatorio")
    private Long idUsu;
    @NotBlank(message = "El nombre de contacto es obligatorio")
    private String nombreContacto;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotNull(message = "El país es obligatorio")
    private Long idPais;

    private Long idUbicacionNivel1;
    private Long idUbicacionNivel2;
    private Long idUbicacionNivel3;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion1;

    private String direccion2;
    
    private String codigoPostal;
    
    private boolean esPrincipal;
}
