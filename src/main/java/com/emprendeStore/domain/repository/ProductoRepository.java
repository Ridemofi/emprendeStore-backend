package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query(value = "SELECT * FROM PRODUCTOS WHERE ESTADO = 'DISPONIBLE' ORDER BY FECHA_REGISTRO DESC, ID_PRO DESC LIMIT 8", nativeQuery = true)
    List<Producto> findRecienLlegados();

    @Query(value = "SELECT * FROM PRODUCTOS WHERE ESTADO = 'DISPONIBLE' AND LOWER(NOMBPRO) LIKE LOWER(CONCAT('%', :nombre, '%'))", nativeQuery = true)
    List<Producto> buscarProducPorNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Producto p WHERE " +
            "(:texto IS NULL OR p.nombreProd LIKE CONCAT('%', :texto, '%')) AND " +
            "(:idCategoria IS NULL OR p.categoria.idCategoria = :idCategoria) AND " +
            "(:idEmprendedor IS NULL OR p.emprendedor.idempre = :idEmprendedor)")
    List<Producto> buscarProConFiltros(
            @Param("texto") String texto,
            @Param("idCategoria") Long idCategoria,
            @Param("idEmprendedor") Long idEmprendedor
    );

}
