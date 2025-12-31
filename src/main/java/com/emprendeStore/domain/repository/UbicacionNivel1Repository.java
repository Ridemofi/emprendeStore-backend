package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.UbicacionNivel1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbicacionNivel1Repository extends JpaRepository<UbicacionNivel1, Long> {
    List<UbicacionNivel1> findByPaisIdPais(Long idPais);
}
