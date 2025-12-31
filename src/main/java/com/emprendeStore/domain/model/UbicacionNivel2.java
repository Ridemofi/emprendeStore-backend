package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UBICACION_NIVEL_2")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UbicacionNivel2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_N2")
    private Long idN2;

    @ManyToOne
    @JoinColumn(name = "ID_N1", nullable = false)
    private UbicacionNivel1 ubicacionNivel1;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;
}
