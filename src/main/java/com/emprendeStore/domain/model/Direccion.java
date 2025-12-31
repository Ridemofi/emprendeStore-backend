package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "DIRECCION", indexes = {
        @Index(name = "IDX_DIR_USU", columnList = "ID_USU"),
        @Index(name = "IDX_DIR_PAIS", columnList = "ID_PAIS")
})
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DIR")
    private Long idDir;

    @ManyToOne
    @JoinColumn(name = "ID_USU", nullable = false)
    private Usuario usuario;

    @Column(name = "NOMBRE_CONTACTO", nullable = false, length = 100)
    private String nombreContacto;

    @Column(name = "TELEFONO_CONTACTO", nullable = false, length = 20)
    private String telefonoContacto;

    @ManyToOne
    @JoinColumn(name = "ID_PAIS", nullable = false)
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "ID_N1")
    private UbicacionNivel1 ubicacionNivel1;

    @ManyToOne
    @JoinColumn(name = "ID_N2")
    private UbicacionNivel2 ubicacionNivel2;

    @ManyToOne
    @JoinColumn(name = "ID_N3")
    private UbicacionNivel3 ubicacionNivel3;

    @Column(name = "DIRECCION1", nullable = false, length = 255)
    private String direccion1;

    @Column(name = "DIRECCION2", length = 255)
    private String direccion2;

    @Column(name = "CODIGO_POSTAL", length = 20)
    private String codigoPostal;

    @Column(name = "ES_PRINCIPAL")
    @Builder.Default
    private Boolean esPrincipal = false;

    @CreationTimestamp
    @Column(name = "FECHA_CREACION", updatable = false)
    private LocalDateTime fechaCreacion;
}
