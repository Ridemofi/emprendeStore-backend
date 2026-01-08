package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.Emprendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmprendedorRepository extends JpaRepository<Emprendedor, Long> {
    @Query(value = "SELECT e.id_empre, e.nombemp, e.imagenempre, e.correo, e.nro_cel, e.password, e.fecha_registro, e.rol, e.saldo " +
            "FROM emprendedor e " +
            "JOIN venta v ON e.id_empre = v.id_empre " +
            "GROUP BY e.id_empre, e.nombemp, e.imagenempre, e.correo, e.nro_cel, e.password, e.fecha_registro, e.rol, e.saldo " +
            "ORDER BY COUNT(v.id_venta) DESC LIMIT 4", nativeQuery = true)
    List<Emprendedor> findEmprePopulares();

    Optional<Emprendedor> findByCorreoemp(String correo);

}
