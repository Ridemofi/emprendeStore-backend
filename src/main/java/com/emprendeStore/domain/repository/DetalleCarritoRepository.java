package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.DetalleCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Long> {
    Optional<DetalleCarrito> findByCarritoIdCarritoAndProductoIdProducto(Long idCarrito, Long idProducto);

}
