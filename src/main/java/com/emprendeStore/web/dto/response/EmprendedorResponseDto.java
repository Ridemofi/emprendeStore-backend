package com.emprendeStore.web.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class EmprendedorResponseDto {

    private Long idemp;
    private String imgemp;
    private String nombep;
    private String correo;
    private String nrocell;
    private LocalDate fechaingreso;
    private BigDecimal saldo;

}
