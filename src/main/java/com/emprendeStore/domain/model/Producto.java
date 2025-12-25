package com.emprendeStore.domain.model;

import com.emprendeStore.application.exception.ErrorNegocio;
import com.emprendeStore.domain.Estados.EstadoProducto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PRODUCTOS")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRO")
    private Long idProducto;
    @Column(name = "NOMBPRO", nullable = false, length = 150)
    private String nombreProd;
    @Column(name = "DESCRPRO")
    @Lob
    private String descrip;
    @Column(name = "PRECIO", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    @Column(name = "STOCKA", nullable = false)
    private int stock;
    @Lob
    @Column(name = "IMGPRO", columnDefinition = "LONGBLOB")
    private byte[] imgPro;
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false)
    private EstadoProducto estadoProducto;
    @CreationTimestamp
    @Column(name = "FECHA_REGISTRO", updatable = false)
    private LocalDate fechaRegistro;
    @ManyToOne
    @JoinColumn(name = "ID_CAT")
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "ID_EMPRE", nullable = false)
    private Emprendedor emprendedor;

    public void cambiarEstado(EstadoProducto nuevoEstado) {
        switch (nuevoEstado) {
            case Pausado -> this.estadoProducto = EstadoProducto.Pausado;
            case Agotado -> {
                this.stock = 0;
                this.estadoProducto = EstadoProducto.Agotado;
            }
            case Disponible -> {
                if (this.stock <= 0) {
                    throw new ErrorNegocio("No se puede marcar como DISPONIBLE un producto sin stock");
                }
                this.estadoProducto = EstadoProducto.Disponible;
            }
            case Bajo -> {
                if (this.stock <= 0) {
                    throw new ErrorNegocio("No se puede marcar como BAJO un producto sin stock");
                }
                this.estadoProducto = EstadoProducto.Bajo;
            }
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
