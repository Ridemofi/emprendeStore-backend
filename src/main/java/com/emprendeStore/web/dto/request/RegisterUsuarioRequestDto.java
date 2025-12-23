package com.emprendeStore.web.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class RegisterUsuarioRequestDto {
    private String nombreReal;
    private String nomUsu;
    private String correo;
    private String nroCel;
    private String password;
    private byte[] img;
}
