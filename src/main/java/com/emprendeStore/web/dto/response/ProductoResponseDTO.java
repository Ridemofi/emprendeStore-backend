package com.emprendeStore.web.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ProductoResponseDTO {
    private Long id;
    private String nombreProd;
    private String descrip;
    private BigDecimal precio;
    private int stock;
    private String imgpro;
    private String estadoProducto;
    private LocalDate fechaRegistro;
    private Long idCategoria;
    private String nombreCategoria;
    private Long idEmprendedor;

}
