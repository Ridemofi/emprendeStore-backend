package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ubicacion_nivel_3")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UbicacionNivel3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_n3")
    private Long idN3;

    @ManyToOne
    @JoinColumn(name = "id_n2", nullable = false)
    private UbicacionNivel2 ubicacionNivel2;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "codigo_postal", length = 20)
    private String codigoPostal;
}
