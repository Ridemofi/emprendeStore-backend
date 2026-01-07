package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Carrito;
import com.emprendeStore.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuarioIdUsu(Long idUsuario);
    Optional<Carrito> findByUsuario(Usuario usuario);
}