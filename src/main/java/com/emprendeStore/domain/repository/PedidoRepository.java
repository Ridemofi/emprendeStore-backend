package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByIdTransaccion(String idTransaccion);

    // Lista simple de pedidos por usuario (para la vista general)
    @Query("SELECT p FROM Pedido p WHERE p.usuario.idUsu = :idUsuario ORDER BY p.fechaPedido DESC")
    List<Pedido> findAllByUsuario(@Param("idUsuario") Long idUsuario);

    // Detalle completo de un pedido específico (para la vista de detalle)
    @Query("SELECT p FROM Pedido p " +
            "LEFT JOIN FETCH p.ventas v " +
            "LEFT JOIN FETCH v.detalles d " +
            "WHERE p.idPedido = :idPedido")
    Optional<Pedido> findByIdWithVentasAndDetalles(@Param("idPedido") Long idPedido);
}