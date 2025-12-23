package com.emprendeStore.web.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaResponseDTO {
    private byte[] imgcat;
    private Long idCategoria;
    private String nombreCat;
}
