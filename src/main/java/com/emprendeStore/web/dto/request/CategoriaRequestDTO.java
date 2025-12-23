package com.emprendeStore.web.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CategoriaRequestDTO {
    private byte[] imgcat;
    private String nombreCat;


}
