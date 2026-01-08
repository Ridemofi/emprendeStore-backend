package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "emprendedor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Emprendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empre")
    private Long idempre;

    @Column(name = "imagenempre")
    private String imgempre;

    @Column(name = "nombemp", length = 40)
    private String nombreemp;

    @Column(name = "correo", nullable = false, length = 40)
    private String correoemp;

    @Column(name = "nro_cel", nullable = false, length = 11)
    private String nrocellemp;

    @Column(name = "password", nullable = false, length = 100)
    private String passwordempre;

    @Column(name = "saldo" , nullable = false)
    private BigDecimal saldo;

    @Column(name = "rol", nullable = false, length = 20)
    @Builder.Default
    private String rol = "EMPRENDEDOR";

    @CreationTimestamp
    @Column(name = "fecha_registro", updatable = false)
    private LocalDate fecharegistroemp;

    @OneToMany(mappedBy = "emprendedor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos;
}
