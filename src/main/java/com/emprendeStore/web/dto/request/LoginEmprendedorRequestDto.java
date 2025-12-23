package com.emprendeStore.web.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class LoginEmprendedorRequestDto {
    private String correo;
    private String password;
}
