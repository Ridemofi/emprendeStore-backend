package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    @Query(value = "SELECT c.id_cat, c.nombre_cat, c.descripcion, c.imgcat " +
            "FROM categoria c " +
            "JOIN productos p ON c.id_cat = p.id_cat " +
            "JOIN detalle_venta dv ON p.id_pro = dv.id_pro " +
            "GROUP BY c.id_cat, c.nombre_cat, c.descripcion, c.imgcat " +
            "ORDER BY SUM(dv.cantidad) DESC LIMIT 4", nativeQuery = true)
    List<Categoria> findCategoriasPopulares();
}
