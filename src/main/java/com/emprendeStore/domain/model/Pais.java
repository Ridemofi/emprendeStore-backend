package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PAISES")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAIS")
    private Long idPais;

    @Column(name = "ISO_CODE", nullable = false, unique = true, length = 2)
    private String isoCode;

    @Column(name = "NOMBRE_PAIS", nullable = false, length = 100)
    private String nombrePais;

    @Column(name = "CODIGO_TELEFONO", length = 10)
    private String codigoTelefono;
}
