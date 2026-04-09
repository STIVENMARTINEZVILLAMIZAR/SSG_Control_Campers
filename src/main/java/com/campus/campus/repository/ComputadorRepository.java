package com.campus.campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campus.campus.model.Computador;
import com.campus.campus.model.EstadoComputador;

public interface ComputadorRepository extends JpaRepository<Computador, Long> {

	boolean existsBySerial(String serial);

	boolean existsByPlacaInventario(String placaInventario);

	long countByEstado(EstadoComputador estado);
}
