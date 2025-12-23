package com.emprendeStore.web.dto.request;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class FavoritoRequestDto {
    private Long idUsu;
    private Long idPro;
}
