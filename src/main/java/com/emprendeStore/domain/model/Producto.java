package com.emprendeStore.domain.model;

import com.emprendeStore.domain.Estados.EstadoProducto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "productos")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pro")
    private Long idProducto;
    @Column(name = "nombpro", nullable = false, length = 150)
    private String nombreProd;
    @Column(name = "descrpro", columnDefinition = "TEXT")
    private String descrip;
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    @Column(name = "stocka", nullable = false)
    private int stock;
    @Column(name = "imgpro", length = 255, nullable = true)
    private String imgPro;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoProducto estadoProducto;
    @CreationTimestamp
    @Column(name = "fecha_registro", updatable = false)
    private LocalDate fechaRegistro;
    @ManyToOne
    @JoinColumn(name = "id_cat", nullable = false)
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "id_empre", nullable = false)
    private Emprendedor emprendedor;

    public void cambiarEstado(EstadoProducto nuevoEstado) {
        switch (nuevoEstado) {
            case Pausado:
                this.estadoProducto = EstadoProducto.Pausado;
                break;
            default:
                this.estadoProducto = EstadoProducto.Disponible;
                break;
        }
    }

    public void recalcularEstado() {
        if (this.estadoProducto == EstadoProducto.Pausado) {
            return;
        }
        if (this.stock <= 0) {
            this.estadoProducto = EstadoProducto.Agotado;
        } else if (this.stock <= 10) {
            this.estadoProducto = EstadoProducto.Bajo;
        } else {
            this.estadoProducto = EstadoProducto.Disponible;
        }
    }
}
