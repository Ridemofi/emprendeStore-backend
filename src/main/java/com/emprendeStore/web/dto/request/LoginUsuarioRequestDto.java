package com.emprendeStore.web.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class LoginUsuarioRequestDto {
    private String identificador;
    private String password;
}
