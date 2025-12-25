package com.emprendeStore.web.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UpdateProductoRequestDto {
    private String nombreProd;
    private String descrip;
    private BigDecimal precio;
    private Integer stock;
    private String imgpro;
    private Long idCategoria;
    private String estado;
}
