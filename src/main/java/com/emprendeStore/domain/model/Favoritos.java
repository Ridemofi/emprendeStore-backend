package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "favoritos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_usu", "id_pro"})
})
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Favoritos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fav")
    private Long idFav;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usu", nullable = false)
    private Usuario usuario;
    /* Igual aquí. Si solo necesitas el ID del producto,
       no traerá toda la info del producto a menos que la pidas.
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pro", nullable = false)
    private Producto producto;
    @CreationTimestamp
    @Column(name = "fecha_agregado", updatable = false)
    private LocalDate fechaAgregado;
}
