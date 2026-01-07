package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "DETALLE_VENTA")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE", nullable = false)
    private Long idDetalle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_VENTA", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PRO", nullable = false)
    private Producto producto;

    @Column(name = "NOMBRE_PRODUCTO", nullable = false, length = 150)
    private String nombreProducto;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    @Column(name = "PRECIO_UNIT", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnit;

    @Column(name = "SUBTOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

}