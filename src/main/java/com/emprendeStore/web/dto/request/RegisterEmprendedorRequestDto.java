package com.emprendeStore.web.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class RegisterEmprendedorRequestDto {
    private String imgEmp;
    private String nombEmp;
    private String descripEmp;
    private String correo;
    private String nrocell;
    private String password;
}
