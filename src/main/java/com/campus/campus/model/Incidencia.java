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
@Table(name = "incidencias")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Incidencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "computador_id", nullable = false)
	private Computador computador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prestamo_id")
	private PrestamoComputador prestamo;

	@Column(nullable = false)
	private LocalDate fechaReporte;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TipoIncidencia tipo;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private SeveridadIncidencia severidad;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private EstadoIncidencia estado = EstadoIncidencia.ABIERTA;

	@Column(nullable = false, length = 500)
	private String descripcion;

	@Column(nullable = false, length = 100)
	private String reportadoPor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Computador getComputador() {
		return computador;
	}

	public void setComputador(Computador computador) {
		this.computador = computador;
	}

	public PrestamoComputador getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(PrestamoComputador prestamo) {
		this.prestamo = prestamo;
	}

	public LocalDate getFechaReporte() {
		return fechaReporte;
	}

	public void setFechaReporte(LocalDate fechaReporte) {
		this.fechaReporte = fechaReporte;
	}

	public TipoIncidencia getTipo() {
		return tipo;
	}

	public void setTipo(TipoIncidencia tipo) {
		this.tipo = tipo;
	}

	public SeveridadIncidencia getSeveridad() {
		return severidad;
	}

	public void setSeveridad(SeveridadIncidencia severidad) {
		this.severidad = severidad;
	}

	public EstadoIncidencia getEstado() {
		return estado;
	}

	public void setEstado(EstadoIncidencia estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getReportadoPor() {
		return reportadoPor;
	}

	public void setReportadoPor(String reportadoPor) {
		this.reportadoPor = reportadoPor;
	}
}
