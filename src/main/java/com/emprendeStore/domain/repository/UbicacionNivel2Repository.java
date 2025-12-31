package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.UbicacionNivel2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbicacionNivel2Repository extends JpaRepository<UbicacionNivel2, Long> {
    List<UbicacionNivel2> findByUbicacionNivel1IdN1(Long idN1);
}
