package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Emprendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmprendedorRepository extends JpaRepository<Emprendedor, Long> {
    @Query(value = "SELECT e.ID_EMPRE, e.NOMBEMP, e.IMAGENEMPRE, e.CORREO, e.NRO_CEL, e.PASSWORD, e.FECHA_REGISTRO, e.ROL " +
            "FROM EMPRENDEDOR e " +
            "JOIN VENTA v ON e.ID_EMPRE = v.ID_EMPRE " +
            "GROUP BY e.ID_EMPRE, e.NOMBEMP, e.IMAGENEMPRE, e.CORREO, e.NRO_CEL, e.PASSWORD, e.FECHA_REGISTRO, e.ROL " +
            "ORDER BY COUNT(v.ID_VENTA) DESC LIMIT 4", nativeQuery = true)
    List<Emprendedor> findEmprePopulares();

    Optional<Emprendedor> findByCorreoemp(String correo);

}
