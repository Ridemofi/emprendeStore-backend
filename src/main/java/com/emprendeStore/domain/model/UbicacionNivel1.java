package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ubicacion_nivel_1")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UbicacionNivel1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_n1")
    private Long idN1;

    @ManyToOne
    @JoinColumn(name = "id_pais", nullable = false)
    private Pais pais;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "code", length = 10)
    private String code;
}
