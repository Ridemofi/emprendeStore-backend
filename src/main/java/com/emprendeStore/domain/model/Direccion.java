package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "direccion", indexes = {
        @Index(name = "idx_dir_usu", columnList = "id_usu"),
        @Index(name = "idx_dir_pais", columnList = "id_pais")
})
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dir")
    private Long idDir;

    @ManyToOne
    @JoinColumn(name = "id_usu", nullable = false)
    private Usuario usuario;

    @Column(name = "nombre_contacto", nullable = false, length = 100)
    private String nombreContacto;

    @Column(name = "telefono_contacto", nullable = false, length = 20)
    private String telefonoContacto;

    @Column(name = "documento_identidad", length = 20)
    private String documentoIdentidad;

    @ManyToOne
    @JoinColumn(name = "id_pais", nullable = false)
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "id_n1")
    private UbicacionNivel1 ubicacionNivel1;

    @ManyToOne
    @JoinColumn(name = "id_n2")
    private UbicacionNivel2 ubicacionNivel2;

    @ManyToOne
    @JoinColumn(name = "id_n3")
    private UbicacionNivel3 ubicacionNivel3;

    @Column(name = "direccion1", nullable = false, length = 255)
    private String direccion1;

    @Column(name = "direccion2", length = 255)
    private String direccion2;

    @Column(name = "codigo_postal", length = 20)
    private String codigoPostal;

    @Column(name = "es_principal")
    @Builder.Default
    private Boolean esPrincipal = false;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;
}
