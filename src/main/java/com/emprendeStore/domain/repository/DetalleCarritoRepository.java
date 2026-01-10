package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.DetalleCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("SELECT d FROM DetalleCarrito d " +
            "JOIN FETCH d.producto p " +
            "JOIN FETCH p.emprendedor " +
            "WHERE d.carrito.usuario.idUsu = :idUsuario AND d.seleccionado = true")
    List<DetalleCarrito> findDetallesSeleccionadosPorUsuario(@Param("idUsuario") Long idUsuario);

    @Modifying // Indica que esto modifica datos, no solo lee
    @Query("UPDATE DetalleCarrito d SET d.seleccionado = :seleccionado " +
            "WHERE d.idDetalleCarrito = :idDetalle " +
            "AND d.carrito.usuario.idUsu = :idUsuario")
    int actualizarSeleccion(@Param("idUsuario") Long idUsuario,
                            @Param("idDetalle") Long idDetalle,
                            @Param("seleccionado") boolean seleccionado);

    @Modifying
    @Query("DELETE FROM DetalleCarrito d WHERE d.carrito.usuario.idUsu = :idUsu AND d.seleccionado = true")
    void deleteSeleccionadosPorUsuario(@Param("idUsu") Long idUsu);

    @Modifying
    @Query("UPDATE DetalleCarrito d SET d.cantidad = :cantidad " +
            "WHERE d.idDetalleCarrito = :idDetalle " +
            "AND d.carrito.usuario.idUsu = :idUsuario")
    int actualizarCantidad(@Param("idUsuario") Long idUsuario,
                           @Param("idDetalle") Long idDetalle,
                           @Param("cantidad") int cantidad);

    @Modifying
    @Query("DELETE FROM DetalleCarrito d " +
            "WHERE d.idDetalleCarrito = :idDetalle " +
            "AND d.carrito.usuario.idUsu = :idUsuario")
    int borrarItemDeUsuario(@Param("idUsuario") Long idUsuario,
                            @Param("idDetalle") Long idDetalle);
}
