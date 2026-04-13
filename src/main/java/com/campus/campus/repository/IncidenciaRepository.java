package com.campus.campus.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campus.campus.model.EstadoIncidencia;
import com.campus.campus.model.Incidencia;
import com.campus.campus.model.SeveridadIncidencia;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {

	long countByEstado(EstadoIncidencia estado);

	long countBySeveridad(SeveridadIncidencia severidad);

	boolean existsByComputadorId(Long computadorId);

	boolean existsByPrestamoId(Long prestamoId);

	long countByComputadorIdAndEstadoInAndSeveridadIn(
			Long computadorId,
			Collection<EstadoIncidencia> estados,
			Collection<SeveridadIncidencia> severidades);
}
