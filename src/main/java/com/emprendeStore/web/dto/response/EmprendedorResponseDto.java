package com.emprendeStore.web.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class EmprendedorResponseDto {

    private Long idemp;
    private byte[] imgemp;
    private String nombep;
    private String correo;
    private String nrocell;
    private LocalDate fechaingreso;

}
