package com.emprendeStore.web.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class FavoritoResponseDto {
    private Long idFav;
    private Long idUsu;
    private Long idPro;
    private String fechaAgregado;
}
