package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.enums.EstadoProducto;
import com.emprendeStore.domain.model.Producto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query("SELECT p FROM Producto p " +
            "JOIN FETCH p.categoria " +
            "LEFT JOIN FETCH p.emprendedor " +
            "WHERE p.estadoProducto = com.emprendeStore.domain.enums.EstadoProducto.Disponible " +
            "ORDER BY p.fechaRegistro DESC, p.idProducto DESC")
    List<Producto> findRecienLlegados(Pageable pageable);

    // Consulta para la búsqueda pública (filtra por estado)
    @Query("SELECT p FROM Producto p " +
            "JOIN FETCH p.categoria " +
            "LEFT JOIN FETCH p.emprendedor " +
            "WHERE " +
            "(p.estadoProducto = com.emprendeStore.domain.enums.EstadoProducto.Disponible OR p.estadoProducto = com.emprendeStore.domain.enums.EstadoProducto.Bajo) AND " +
            "(:texto IS NULL OR p.nombreProd LIKE CONCAT('%', :texto, '%')) AND " +
            "(:idCategoria IS NULL OR p.categoria.idCategoria = :idCategoria) AND " +
            "(:idEmprendedor IS NULL OR p.emprendedor.idempre = :idEmprendedor)")
    List<Producto> buscarProConFiltros(
            @Param("texto") String texto,
            @Param("idCategoria") Long idCategoria,
            @Param("idEmprendedor") Long idEmprendedor
    );

    // NUEVA CONSULTA: Para la gestión del emprendedor (NO filtra por estado)
    @Query("SELECT p FROM Producto p " +
            "JOIN FETCH p.categoria " +
            "WHERE " +
            "p.emprendedor.idempre = :idEmprendedor AND " +
            "(:texto IS NULL OR p.nombreProd LIKE CONCAT('%', :texto, '%'))")
    List<Producto> buscarProductParaGestion(
            @Param("idEmprendedor") Long idEmprendedor,
            @Param("texto") String texto
    );

    /**
     * Cuenta el número de productos con un estado específico para un emprendedor.
     * Spring Data JPA genera la consulta automáticamente.
     * Ejemplo de uso: countByEmprendedorIdempreAndEstadoProducto(1L, EstadoProducto.BAJO);
     */
    long countByEmprendedorIdempreAndEstadoProducto(Long idEmprendedor, EstadoProducto estado);

    @Query("SELECT COALESCE(SUM(p.precio * p.stock), 0) FROM Producto p WHERE p.emprendedor.idempre = :idEmprendedor")
    BigDecimal calcularValorTotalInventarioByEmprendedor(@Param("idEmprendedor") Long idEmprendedor);

    long countByEmprendedorIdempre(Long idEmprendedor);

    @Query("SELECT p FROM Producto p " +
            "JOIN FETCH p.categoria " +
            "LEFT JOIN FETCH p.emprendedor " +
            "WHERE p.categoria.idCategoria IN :catIds " +
            "AND p.idProducto NOT IN :excludedIds " +
            "AND p.estadoProducto = com.emprendeStore.domain.enums.EstadoProducto.Disponible " +
            "ORDER BY FUNCTION('RAND')")
    List<Producto> findRecomendaciones(
            @Param("catIds") List<Long> catIds,
            @Param("excludedIds") List<Long> excludedIds,
            Pageable pageable
    );
}
