package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "FAVORITOS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ID_USU", "ID_PRO"})
})
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Favoritos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FAV")
    private Long idFav;
    @ManyToOne
    @JoinColumn(name = "ID_USU", nullable = false)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "ID_PRO", nullable = false)
    private Producto producto;
    @CreationTimestamp
    @Column(name = "FECHA_AGREGADO", updatable = false)
    private LocalDate fechaAgregado;
}
