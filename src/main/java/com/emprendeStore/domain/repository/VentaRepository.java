package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByPedidoIdTransaccion(String idTransaccion);

    //lo mas reciente primero
    @Query("SELECT v FROM Venta v JOIN FETCH v.pedido p JOIN FETCH p.usuario u WHERE v.emprendedor.idempre = :idEmprendedor ORDER BY p.fechaPedido DESC")
    List<Venta> findAllByEmprendedor(@Param("idEmprendedor") Long idEmprendedor);
}