package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "paises")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pais")
    private Long idPais;

    @Column(name = "iso_code", nullable = false, unique = true, length = 2)
    private String isoCode;

    @Column(name = "nombre_pais", nullable = false, length = 100)
    private String nombrePais;

    @Column(name = "codigo_telefono", length = 10)
    private String codigoTelefono;

    @OneToOne(mappedBy = "pais", fetch = FetchType.LAZY)
    private ConfigPais configPais;
}
