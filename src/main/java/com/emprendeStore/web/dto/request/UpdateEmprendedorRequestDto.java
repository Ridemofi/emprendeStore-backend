package com.emprendeStore.web.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UpdateEmprendedorRequestDto {
    private String nombEmpre;
    private String descripEmpre;
    private String correo;
    private String telefono;

}
