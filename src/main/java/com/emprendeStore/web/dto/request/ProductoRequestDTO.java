package com.emprendeStore.web.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ProductoRequestDTO {
    private String nombreProd;
    private String descrip;
    private BigDecimal precio;
    private int stock;
    private byte[] imgpro;
    private Long idCategoria;
    private Long idEmprendedor;

}
