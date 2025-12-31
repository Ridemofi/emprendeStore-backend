package com.emprendeStore.web.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ConfigPaisResponseDto {
    private Boolean usaNivel1;
    private Boolean usaNivel2;
    private Boolean usaNivel3;
    private String labelNivel1;
    private String labelNivel2;
    private String labelNivel3;
    private Boolean zipRequerido;
    private String formatoTelefono;
}
