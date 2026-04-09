package com.campus.campus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campus.campus.model.Camper;

public interface CamperRepository extends JpaRepository<Camper, Long> {

	Optional<Camper> findByDocumento(String documento);

	boolean existsByDocumento(String documento);

	boolean existsByCorreo(String correo);

	long countByActivoTrue();
}
