package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByIdTransaccion(String idTransaccion);
}
