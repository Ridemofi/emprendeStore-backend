package com.emprendeStore.web.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class RegisterEmprendedorRequestDto {
    private String imgemp;
    private String nombep;
    private String correo;
    private String nrocell;
    private String password;
}
