package com.emprendeStore.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UpdateProductoRequestDto {

    @Size(max = 150, message = "El nombre no puede exceder los 150 caracteres")
    private String nombreProd;
    private String descrip;
    @Positive(message = "El precio debe ser un valor positivo")
    private BigDecimal precio;
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
    private String imgpro;
    private Long idCategoria;
    private String estado;
}
