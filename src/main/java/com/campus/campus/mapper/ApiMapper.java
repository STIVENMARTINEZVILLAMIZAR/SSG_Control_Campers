package com.campus.campus.mapper;

import org.springframework.stereotype.Component;

import com.campus.campus.dto.CamperResponse;
import com.campus.campus.dto.CamperResumenResponse;
import com.campus.campus.dto.ComputadorResponse;
import com.campus.campus.dto.ComputadorResumenResponse;
import com.campus.campus.dto.IncidenciaResponse;
import com.campus.campus.dto.PrestamoResponse;
import com.campus.campus.dto.UsuarioResponse;
import com.campus.campus.model.Camper;
import com.campus.campus.model.Computador;
import com.campus.campus.model.Incidencia;
import com.campus.campus.model.PrestamoComputador;
import com.campus.campus.model.Usuario;

@Component
public class ApiMapper {

	public CamperResponse toResponse(Camper camper) {
		return new CamperResponse(
				camper.getId(),
				camper.getDocumento(),
				camper.getNombreCompleto(),
				camper.getCorreo(),
				camper.getClan(),
				camper.isActivo());
	}

	public CamperResumenResponse toResumen(Camper camper) {
		return new CamperResumenResponse(
				camper.getId(),
				camper.getDocumento(),
				camper.getNombreCompleto());
	}

	public ComputadorResponse toResponse(Computador computador) {
		return new ComputadorResponse(
				computador.getId(),
				computador.getSerial(),
				computador.getPlacaInventario(),
				computador.getMarca(),
				computador.getModelo(),
				computador.getUbicacion(),
				computador.getEstado());
	}

	public ComputadorResumenResponse toResumen(Computador computador) {
		return new ComputadorResumenResponse(
				computador.getId(),
				computador.getSerial(),
				computador.getPlacaInventario(),
				computador.getEstado());
	}

	public PrestamoResponse toResponse(PrestamoComputador prestamo) {
		return new PrestamoResponse(
				prestamo.getId(),
				toResumen(prestamo.getCamper()),
				toResumen(prestamo.getComputador()),
				prestamo.getFechaAsignacion(),
				prestamo.getFechaDevolucionProgramada(),
				prestamo.getFechaEntregaReal(),
				prestamo.getEstado(),
				prestamo.getObservaciones());
	}

	public IncidenciaResponse toResponse(Incidencia incidencia) {
		return new IncidenciaResponse(
				incidencia.getId(),
				toResumen(incidencia.getComputador()),
				incidencia.getPrestamo() != null ? incidencia.getPrestamo().getId() : null,
				incidencia.getFechaReporte(),
				incidencia.getTipo(),
				incidencia.getSeveridad(),
				incidencia.getEstado(),
				incidencia.getDescripcion(),
				incidencia.getReportadoPor());
	}

	public UsuarioResponse toResponse(Usuario usuario) {
		return new UsuarioResponse(
				usuario.getId(),
				usuario.getNombreCompleto(),
				usuario.getCorreo(),
				usuario.getRol(),
				usuario.isActivo());
	}
}
