package com.campus.campus.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prestamos_computador")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PrestamoComputador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "camper_id", nullable = false)
	private Camper camper;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "computador_id", nullable = false)
	private Computador computador;

	@Column(nullable = false)
	private LocalDate fechaAsignacion;

	@Column(nullable = false)
	private LocalDate fechaDevolucionProgramada;

	@Column
	private LocalDate fechaEntregaReal;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private EstadoPrestamo estado = EstadoPrestamo.ACTIVO;

	@Column(length = 250)
	private String observaciones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Camper getCamper() {
		return camper;
	}

	public void setCamper(Camper camper) {
		this.camper = camper;
	}

	public Computador getComputador() {
		return computador;
	}

	public void setComputador(Computador computador) {
		this.computador = computador;
	}

	public LocalDate getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(LocalDate fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public LocalDate getFechaDevolucionProgramada() {
		return fechaDevolucionProgramada;
	}

	public void setFechaDevolucionProgramada(LocalDate fechaDevolucionProgramada) {
		this.fechaDevolucionProgramada = fechaDevolucionProgramada;
	}

	public LocalDate getFechaEntregaReal() {
		return fechaEntregaReal;
	}

	public void setFechaEntregaReal(LocalDate fechaEntregaReal) {
		this.fechaEntregaReal = fechaEntregaReal;
	}

	public EstadoPrestamo getEstado() {
		return estado;
	}

	public void setEstado(EstadoPrestamo estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}
