package com.jadylima.consultacep.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "consultas_cep")
public class ConsultaCep {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 8)
	private String cep;

	private String logradouro;

	private String bairro;

	private String cidade;

	@Column(name = "data_consulta", nullable = false)
	private LocalDateTime dataConsulta;

	protected ConsultaCep() {}

	public ConsultaCep(String cep, String logradouro, String bairro, String cidade, LocalDateTime dataConsulta) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.cidade = cidade;
		this.dataConsulta = dataConsulta;
	}

	public Long getId() {
		return id;
	}

	public String getCep() {
		return cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public LocalDateTime getDataConsulta() {
		return dataConsulta;
	}

}
