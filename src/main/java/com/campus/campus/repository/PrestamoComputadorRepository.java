package com.campus.campus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campus.campus.model.EstadoPrestamo;
import com.campus.campus.model.PrestamoComputador;

public interface PrestamoComputadorRepository extends JpaRepository<PrestamoComputador, Long> {

	boolean existsByCamperIdAndEstado(Long camperId, EstadoPrestamo estado);

	boolean existsByComputadorIdAndEstado(Long computadorId, EstadoPrestamo estado);

	boolean existsByCamperId(Long camperId);

	boolean existsByComputadorId(Long computadorId);

	Optional<PrestamoComputador> findByIdAndEstado(Long id, EstadoPrestamo estado);

	Optional<PrestamoComputador> findByComputadorIdAndEstado(Long computadorId, EstadoPrestamo estado);

	List<PrestamoComputador> findByEstado(EstadoPrestamo estado);

	long countByEstado(EstadoPrestamo estado);
}
