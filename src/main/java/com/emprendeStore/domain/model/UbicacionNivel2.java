package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ubicacion_nivel_2")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UbicacionNivel2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_n2")
    private Long idN2;

    @ManyToOne
    @JoinColumn(name = "id_n1", nullable = false)
    private UbicacionNivel1 ubicacionNivel1;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
}
