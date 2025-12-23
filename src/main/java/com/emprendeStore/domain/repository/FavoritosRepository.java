package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Favoritos;
import com.emprendeStore.domain.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoritosRepository extends JpaRepository<Favoritos, Long> {
    @Query("SELECT f.producto FROM Favoritos f WHERE f.usuario.idUsu = :idUsu")
    List<Producto> findProductosFavoritosPorUsuario(@Param("idUsu") Long idUsu);
    /**
     * Verifica si existe un favorito específico (útil para evitar duplicados
     * o para saber si pintar el corazón de rojo en el frontend).
     * Se basa en: Favoritos -> usuario -> idUsu Y Favoritos -> producto -> idProducto
     */
    Optional<Favoritos> findByUsuarioIdUsuAndProductoIdProducto(Long idUsu, Long idProducto);

    /**
     * Elimina un favorito específico (cuando el usuario desmarca el corazón).
     */
    void deleteByUsuarioIdUsuAndProductoIdProducto(Long idUsu, Long idProducto);
}
