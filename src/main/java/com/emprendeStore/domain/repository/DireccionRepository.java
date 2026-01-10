package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    long countByUsuarioIdUsu(Long idUsu);

    @Query("SELECT d FROM Direccion d " +
            "JOIN FETCH d.pais p " +              // Trae el País
            "LEFT JOIN FETCH p.configPais " +     // <--- AGREGA ESTO: Trae la config del país también
            "LEFT JOIN FETCH d.ubicacionNivel1 " +
            "LEFT JOIN FETCH d.ubicacionNivel2 " +
            "LEFT JOIN FETCH d.ubicacionNivel3 " +
            "WHERE d.usuario.idUsu = :idUsu")
    List<Direccion> findAllConDetallesPorUsuario(@Param("idUsu") Long idUsu);

    @Modifying
    @Query("UPDATE Direccion d SET d.esPrincipal = false WHERE d.usuario.idUsu = :idUsu")
    void desmarcarTodasLasDelUsuario(@Param("idUsu") Long idUsu);

    @Query("SELECT d FROM Direccion d " +
            "JOIN FETCH d.pais " +
            "LEFT JOIN FETCH d.ubicacionNivel1 " +
            "LEFT JOIN FETCH d.ubicacionNivel2 " +
            "LEFT JOIN FETCH d.ubicacionNivel3 " +
            "WHERE d.usuario.idUsu = :idUsu AND d.esPrincipal = true")
    Optional<Direccion> findDireccionPrincipalConDetalles(@Param("idUsu") Long idUsu);

    @Query("SELECT d FROM Direccion d " +
            "JOIN FETCH d.pais " +
            "LEFT JOIN FETCH d.ubicacionNivel1 " +
            "LEFT JOIN FETCH d.ubicacionNivel2 " +
            "LEFT JOIN FETCH d.ubicacionNivel3 " +
            "WHERE d.idDir = :idDir")
    Optional<Direccion> findByIdWithDetalles(@Param("idDir") Long idDir);

}
