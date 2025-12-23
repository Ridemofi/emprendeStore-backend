package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "DETALLE_CARRITO", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ID_CARRITO", "ID_PRO"})
})
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class DetalleCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_CARRITO")
    private Long idDetalleCarrito;
    @ManyToOne
    @JoinColumn(name = "ID_CARRITO", nullable = false)
    private Carrito carrito;
    @ManyToOne
    @JoinColumn(name = "ID_PRO", nullable = false)
    private Producto producto;
    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;
    @Column(name = "PRECIO_UNIDAD", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;
}