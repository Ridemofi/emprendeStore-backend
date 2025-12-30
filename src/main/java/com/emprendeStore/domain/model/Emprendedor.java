package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "EMPRENDEDOR")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Emprendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPRE")
    private Long idempre;

    @Column(name = "IMAGENEMPRE")
    private String imgempre;

    @Column(name = "NOMBEMP", length = 40)
    private String nombreemp;

    @Column(name = "CORREO", nullable = false, length = 40)
    private String correoemp;

    @Column(name = "NRO_CEL", nullable = false, length = 11)
    private String nrocellemp;

    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String passwordempre;

    @Column(name = "ROL", nullable = false, length = 20)
    @Builder.Default
    private String rol = "EMPRENDEDOR";

    @CreationTimestamp
    @Column(name = "FECHA_REGISTRO", updatable = false)
    private LocalDate fecharegistroemp;

    @OneToMany(mappedBy = "emprendedor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos;
}
