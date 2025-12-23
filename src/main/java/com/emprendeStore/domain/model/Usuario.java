package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USU")
    private Long idUsu;

    @Lob
    @Column(name = "IMAGENUSU", columnDefinition = "LONGBLOB")
    private byte[] imgUsu;

    @Column(name = "NOMBREREAL", nullable = false, length = 30)
    private String nombReal;

    @Column(name = "USUARIO", nullable = false, length = 30)
    private String nomUsu;

    @Column(name = "CORREO", nullable = false, length = 40)
    private String correo;

    @Column(name = "NRO_CEL", nullable = false, length = 11)
    private String nroCel;

    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    @CreationTimestamp
    @Column(name = "FECHA_REGISTRO", updatable = false)
    private LocalDate fechaRegistro;

}
