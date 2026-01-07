package com.emprendeStore.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "CATEGORIA")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CAT")
    private Long idCategoria;
    @Column(name = "IMGCAT", columnDefinition = "LONGBLOB")
    @Lob
    private byte[] imgCat;
    @Column(name = "NOMBRE_CAT", nullable = false, unique = true, length = 100)
    private String nombreCat;
    @Column(name = "DESCRIPCION", columnDefinition = "TEXT")
    private String descripcion;
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos;
}
