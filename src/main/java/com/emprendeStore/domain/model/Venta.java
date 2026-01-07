package com.emprendeStore.domain.model;

import com.emprendeStore.domain.Estados.EstadoVenta;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "VENTA")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA", nullable = false)
    private Long idVenta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PEDIDO", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_EMPRE", nullable = false)
    private Emprendedor emprendedor;

    @ColumnDefault("0.00")
    @Column(name = "SUBTOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @ColumnDefault("0.00")
    @Column(name = "TOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @ColumnDefault("'PENDIENTE'")
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_VENTA", nullable = false, length = 20)
    private EstadoVenta estadoVenta;

    @Column(name = "EMPRESA_ENVIO", length = 100)
    private String empresaEnvio;

    @Column(name = "CODIGO_SEGUIMIENTO", length = 100)
    private String codigoSeguimiento;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<DetalleVenta> detalles = new ArrayList<>();
}
