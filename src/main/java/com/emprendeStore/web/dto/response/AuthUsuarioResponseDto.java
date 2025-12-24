package com.emprendeStore.web.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class AuthUsuarioResponseDto {
    private Long id;
    private String img;
    private String nombreReal;
    private String nomUsu;
    private String correo;
    private String nroCel;
    private LocalDate fechaRegistro;
    private String token;
    private String rol;
    @Builder.Default
    private String tokenType = "Bearer";
}
