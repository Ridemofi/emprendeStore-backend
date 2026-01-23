package com.emprendeStore.domain.model;

import com.emprendeStore.domain.enums.Rol;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usu")
    private Long idUsu;

    @Column(name = "imagenusu", length = 255, nullable = true)
    private String imagenUsu;

    @Column(name = "nombrereal", nullable = false, length = 30)
    private String nombReal;

    @Column(name = "correo", nullable = false, length = 40)
    private String correo;

    @Column(name = "nro_cel", length = 11, nullable = true)
    private String nroCel;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @CreationTimestamp
    @Column(name = "fecha_registro", updatable = false)
    private LocalDate fechaRegistro;

}
