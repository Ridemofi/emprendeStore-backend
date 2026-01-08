package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuarioIdUsu(Long idUsuario);

    @Query("SELECT c FROM Carrito c " +
            "LEFT JOIN FETCH c.detalles d " +
            "LEFT JOIN FETCH d.producto p " +
            "WHERE c.usuario.idUsu = :idUsu")
    Optional<Carrito> findByUsuarioWithDetalles(@Param("idUsu") Long idUsu);
}