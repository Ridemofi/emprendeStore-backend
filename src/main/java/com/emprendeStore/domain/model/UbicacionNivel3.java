package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UBICACION_NIVEL_3")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UbicacionNivel3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_N3")
    private Long idN3;

    @ManyToOne
    @JoinColumn(name = "ID_N2", nullable = false)
    private UbicacionNivel2 ubicacionNivel2;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "CODIGO_POSTAL", length = 20)
    private String codigoPostal;
}
