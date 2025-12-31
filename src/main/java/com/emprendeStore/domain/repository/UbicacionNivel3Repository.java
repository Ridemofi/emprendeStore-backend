package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.UbicacionNivel3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbicacionNivel3Repository extends JpaRepository<UbicacionNivel3, Long> {
    List<UbicacionNivel3> findByUbicacionNivel2IdN2(Long idN2);
}
