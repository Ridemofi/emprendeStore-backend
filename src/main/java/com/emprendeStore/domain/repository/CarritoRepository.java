package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuarioIdUsu(Long idUsuario);
}
