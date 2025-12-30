package com.emprendeStore.web.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ProductoRequestDTO {
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(max = 150, message = "El nombre no puede exceder los 150 caracteres")
    private String nombreProd;
    private String descrip;
    @NotNull(message = "El precio no puede ser nulo")
    @Positive(message = "El precio debe ser un valor positivo")
    private BigDecimal precio;
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;
    // La imagen es opcional
    private String imgpro;
    @NotNull(message = "El ID de la categoría no puede ser nulo")
    private Long idCategoria;
    @NotNull(message = "El ID del emprendedor no puede ser nulo")
    private Long idEmprendedor;

}
