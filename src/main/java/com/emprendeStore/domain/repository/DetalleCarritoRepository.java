package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.DetalleCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Long> {

    // Total de todos los ítems del carrito (por si lo necesitas en el futuro)
    @Query("SELECT COALESCE(SUM(d.cantidad * d.precioUnitario), 0) " +
            "FROM DetalleCarrito d " +
            "WHERE d.carrito.usuario.idUsu = :idUsuario")
    BigDecimal obtenerSubtotalTotalPorUsuario(@Param("idUsuario") Long idUsuario);

    // Total solo de los ítems seleccionados del carrito
    @Query("SELECT COALESCE(SUM(d.cantidad * d.precioUnitario), 0) " +
            "FROM DetalleCarrito d " +
            "WHERE d.carrito.usuario.idUsu = :idUsuario " +
            "AND d.seleccionado = true")
    BigDecimal obtenerSubtotalSeleccionadosPorUsuario(@Param("idUsuario") Long idUsuario);
}
