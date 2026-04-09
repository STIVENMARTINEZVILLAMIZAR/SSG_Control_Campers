package com.campus.campus.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.campus.campus.model.Camper;
import com.campus.campus.model.Computador;
import com.campus.campus.model.EstadoComputador;
import com.campus.campus.model.EstadoPrestamo;
import com.campus.campus.model.PrestamoComputador;
import com.campus.campus.repository.CamperRepository;
import com.campus.campus.repository.ComputadorRepository;
import com.campus.campus.repository.PrestamoComputadorRepository;

@Configuration
public class DataLoader {

	@Bean
	CommandLineRunner initData(
			CamperRepository camperRepository,
			ComputadorRepository computadorRepository,
			PrestamoComputadorRepository prestamoRepository) {
		return args -> {
			if (camperRepository.count() > 0 || computadorRepository.count() > 0) {
				return;
			}

			Camper camper1 = new Camper();
			camper1.setDocumento("109000001");
			camper1.setNombreCompleto("Laura Martinez");
			camper1.setCorreo("laura.martinez@campuslands.com");
			camper1.setClan("Cajasan-Backend");
			camperRepository.save(camper1);

			Camper camper2 = new Camper();
			camper2.setDocumento("109000002");
			camper2.setNombreCompleto("Nicolas Rojas");
			camper2.setCorreo("nicolas.rojas@campuslands.com");
			camper2.setClan("Cajasan-Frontend");
			camperRepository.save(camper2);

			Computador computador1 = new Computador();
			computador1.setSerial("SN-CAJ-1001");
			computador1.setPlacaInventario("INV-001");
			computador1.setMarca("Lenovo");
			computador1.setModelo("ThinkPad E14");
			computador1.setUbicacion("Sala 1");
			computador1.setEstado(EstadoComputador.ASIGNADO);
			computadorRepository.save(computador1);

			Computador computador2 = new Computador();
			computador2.setSerial("SN-CAJ-1002");
			computador2.setPlacaInventario("INV-002");
			computador2.setMarca("HP");
			computador2.setModelo("ProBook 440");
			computador2.setUbicacion("Sala 2");
			computadorRepository.save(computador2);

			PrestamoComputador prestamo = new PrestamoComputador();
			prestamo.setCamper(camper1);
			prestamo.setComputador(computador1);
			prestamo.setFechaAsignacion(LocalDate.now());
			prestamo.setFechaDevolucionProgramada(LocalDate.now().plusDays(7));
			prestamo.setEstado(EstadoPrestamo.ACTIVO);
			prestamo.setObservaciones("Prestamo inicial de ejemplo");
			prestamoRepository.save(prestamo);
		};
	}
}
