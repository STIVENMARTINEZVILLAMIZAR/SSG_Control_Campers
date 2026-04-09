package com.campus.campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campus.campus.model.EstadoIncidencia;
import com.campus.campus.model.Incidencia;
import com.campus.campus.model.SeveridadIncidencia;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {

	long countByEstado(EstadoIncidencia estado);

	long countBySeveridad(SeveridadIncidencia severidad);
}
