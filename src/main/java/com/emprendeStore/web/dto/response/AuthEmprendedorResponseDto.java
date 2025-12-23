package com.emprendeStore.web.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class AuthEmprendedorResponseDto {
    private Long idemp;
    private String imgemp;
    private String nombep;
    private String correo;
    private String nrocell;
    private LocalDate fechaingreso;
    private String token;
    @Builder.Default
    private String tokenType = "Bearer";
}