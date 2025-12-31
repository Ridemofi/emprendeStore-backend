package com.emprendeStore.domain.repository;

import com.emprendeStore.domain.model.ConfigPais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigPaisRepository extends JpaRepository<ConfigPais, Long> {
}
