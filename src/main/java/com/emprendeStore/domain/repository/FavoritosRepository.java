package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Favoritos;
import com.emprendeStore.domain.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritosRepository extends JpaRepository<Favoritos, Long> {
    /* * OPTIMIZACIÓN N+1:
     * 1. JOIN f.producto p: Une la tabla favoritos con productos.
     * 2. LEFT JOIN FETCH p.categoria: Obliga a Hibernate a traer los datos de la Categoria YA.
     * 3. LEFT JOIN FETCH p.emprendedor: Obliga a Hibernate a traer los datos del Emprendedor YA.
     */
    @Query("SELECT p FROM Favoritos f " +
            "JOIN f.producto p " +
            "LEFT JOIN FETCH p.categoria " +
            "LEFT JOIN FETCH p.emprendedor " +
            "WHERE f.usuario.idUsu = :idUsu")
    List<Producto> findProductosFavoritosPorUsuario(@Param("idUsu") Long idUsu);

    @Modifying // Indica que es una operación de escritura
    @Query("DELETE FROM Favoritos f WHERE f.usuario.idUsu = :idUsu AND f.producto.idProducto = :idPro")
    void deleteFavoritoDirecto(@Param("idUsu") Long idUsu, @Param("idPro") Long idPro);

    // Mantén este para la validación de existencia si quieres,
    // pero asegúrate de tener un índice en la BD.
    boolean existsByUsuarioIdUsuAndProductoIdProducto(Long idUsu, Long idPro);
}
