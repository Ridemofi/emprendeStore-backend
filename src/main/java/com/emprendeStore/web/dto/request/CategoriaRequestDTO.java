package com.emprendeStore.web.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CategoriaRequestDTO {
    private String imgcat;
    private String nombreCat;
    private String descripcion;
}
