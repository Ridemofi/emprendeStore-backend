package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_carrito", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_carrito", "id_pro"})
})
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DetalleCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_carrito")
    private Long idDetalleCarrito;
    @ManyToOne
    @JoinColumn(name = "id_carrito", nullable = false)
    private Carrito carrito;
    @ManyToOne
    @JoinColumn(name = "id_pro", nullable = false)
    private Producto producto;
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    @Column(name = "precio_unidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "seleccionado", nullable = false)
    @Builder.Default
    private Boolean seleccionado = true;
}
