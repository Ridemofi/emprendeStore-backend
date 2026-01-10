package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categoria")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cat")
    private Long idCategoria;
    @Column(name = "imgcat")
    private String imgCat;
    @Column(name = "nombre_cat", nullable = false, unique = true, length = 100)
    private String nombreCat;
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos;
}
