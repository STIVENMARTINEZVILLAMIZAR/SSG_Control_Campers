package com.campus.campus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "computadores")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Computador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50, unique = true)
	private String serial;

	@Column(nullable = false, length = 30, unique = true)
	private String placaInventario;

	@Column(nullable = false, length = 60)
	private String marca;

	@Column(nullable = false, length = 60)
	private String modelo;

	@Column(nullable = false, length = 80)
	private String ubicacion;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private EstadoComputador estado = EstadoComputador.DISPONIBLE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getPlacaInventario() {
		return placaInventario;
	}

	public void setPlacaInventario(String placaInventario) {
		this.placaInventario = placaInventario;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public EstadoComputador getEstado() {
		return estado;
	}

	public void setEstado(EstadoComputador estado) {
		this.estado = estado;
	}
}
