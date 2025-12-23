package com.emprendeStore.web.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UpdateUsuarioRequestDto {
    private String imgusu;
    private String nombreReal;
    private String nomUsu;
    private String correo;
    private String nroCel;

}
