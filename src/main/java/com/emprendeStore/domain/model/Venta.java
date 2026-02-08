package com.emprendeStore.domain.model;

import com.emprendeStore.domain.enums.EstadoVenta;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "venta")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta", nullable = false)
    private Long idVenta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_empre", nullable = false)
    private Emprendedor emprendedor;

    @Column(name = "nombemp", length = 40)
    private String nombreEmprendedorSnapshot;

    @ColumnDefault("0.00")
    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @ColumnDefault("0.00")
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @ColumnDefault("'PENDIENTE'")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_venta", nullable = false, length = 20)
    private EstadoVenta estadoVenta;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<DetalleVenta> detalles = new HashSet<>();
}
