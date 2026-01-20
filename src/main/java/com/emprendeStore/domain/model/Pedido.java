package com.emprendeStore.domain.model;

import com.emprendeStore.domain.Estados.EstadoPedido;
import com.emprendeStore.domain.Estados.MetodoPago;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "pedido", indexes = {
        @Index(name = "idx_pedido_transaccion", columnList = "id_transaccion", unique = true)
})
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usu", nullable = false)
    private Usuario usuario;

    @Column(name = "id_transaccion", nullable = false, length = 100, unique = true)
    private String idTransaccion;

    @CreationTimestamp
    @Column(name = "fecha_pedido", updatable = false)
    private LocalDateTime fechaPedido;

    @ColumnDefault("0.00")
    @Column(name = "subtotal_global", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotalGlobal;

    @ColumnDefault("0.00")
    @Column(name = "costo_envio_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoEnvioTotal;

    @ColumnDefault("0.00")
    @Column(name = "total_pagado", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPagado;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @ColumnDefault("'PENDIENTE'")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pedido", nullable = false, length = 20)
    private EstadoPedido estadoPedido;

    @Column(name = "direccion_snapshot", length = 1000)
    private String direccionSnapshot;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Venta> ventas = new ArrayList<>();
}
