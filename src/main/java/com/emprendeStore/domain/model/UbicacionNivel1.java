package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UBICACION_NIVEL_1")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UbicacionNivel1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_N1")
    private Long idN1;

    @ManyToOne
    @JoinColumn(name = "ID_PAIS", nullable = false)
    private Pais pais;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "CODE", length = 10)
    private String code;
}
