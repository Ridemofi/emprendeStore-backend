package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    @Query(value = "SELECT c.ID_CAT, c.NOMBRE_CAT, c.DESCRIPCION, c.IMGCAT " +
            "FROM CATEGORIA c " +
            "JOIN PRODUCTOS p ON c.ID_CAT = p.ID_CAT " +
            "JOIN DETALLE_VENTA dv ON p.ID_PRO = dv.ID_PRO " +
            "GROUP BY c.ID_CAT, c.NOMBRE_CAT, c.DESCRIPCION, c.IMGCAT " +
            "ORDER BY SUM(dv.CANTIDAD) DESC LIMIT 4", nativeQuery = true)
    List<Categoria> findCategoriasPopulares();
}
