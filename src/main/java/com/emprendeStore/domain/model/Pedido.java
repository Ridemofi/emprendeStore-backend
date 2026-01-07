package com.emprendeStore.domain.model;

import com.emprendeStore.domain.Estados.EstadoVenta;
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
@Table(name = "PEDIDO", indexes = {
        @Index(name = "IDX_PEDIDO_TRANSACCION", columnList = "ID_TRANSACCION", unique = true)
})
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO", nullable = false)
    private Long idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USU", nullable = false)
    private Usuario usuario;

    @Column(name = "ID_TRANSACCION", nullable = false, length = 100, unique = true)
    private String idTransaccion;

    @CreationTimestamp
    @Column(name = "FECHA_PEDIDO", updatable = false)
    private LocalDateTime fechaPedido;

    @ColumnDefault("0.00")
    @Column(name = "SUBTOTAL_GLOBAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotalGlobal;

    @ColumnDefault("0.00")
    @Column(name = "COSTO_ENVIO_TOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoEnvioTotal;

    @ColumnDefault("0.00")
    @Column(name = "TOTAL_PAGADO", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPagado;

    @Enumerated(EnumType.STRING)
    @Column(name = "METODO_PAGO", nullable = false)
    private MetodoPago metodoPago;

    @ColumnDefault("'PENDIENTE'")
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_PEDIDO", nullable = false, length = 20)
    private EstadoVenta estadoPedido;

    @Column(name = "DIRECCION_SNAPSHOT", length = 1000)
    private String direccionSnapshot;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Venta> ventas = new ArrayList<>();
}
