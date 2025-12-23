package com.emprendeStore.web.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UsuarioResponseDto {
    private Long id;
    private String img;
    private String nombreReal;
    private String nomUsu;
    private String correo;
    private String nroCel;
    private LocalDate fechaRegistro;
}
