package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.DetalleCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Long> {

    // Total solo de los ítems seleccionados del carrito
    @Query("SELECT COALESCE(SUM(d.cantidad * d.precioUnitario), 0) " +
            "FROM DetalleCarrito d " +
            "WHERE d.carrito.usuario.idUsu = :idUsuario " +
            "AND d.seleccionado = true")
    BigDecimal obtenerSubtotalSeleccionadosPorUsuario(@Param("idUsuario") Long idUsuario);

    // Calcular el costo de envío: devuelve 20 si el subtotal < 40, de lo contrario 0
    @Query("SELECT CASE " +
            "WHEN COALESCE(SUM(d.cantidad * d.precioUnitario), 0) < 40 THEN 20.0 " +
            "ELSE 0.0 END " +
            "FROM DetalleCarrito d " +
            "WHERE d.carrito.usuario.idUsu = :idUsuario " +
            "AND d.seleccionado = true")
    BigDecimal calcularCostoEnvioPorUsuario(@Param("idUsuario") Long idUsuario);

    @Query("SELECT d FROM DetalleCarrito d WHERE d.carrito.usuario.idUsu = :idUsuario AND d.seleccionado = true")
    List<DetalleCarrito> findDetallesSeleccionadosPorUsuario(@Param("idUsuario") Long idUsuario);
}
