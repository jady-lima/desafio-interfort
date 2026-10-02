package com.jadylima.consultacep.dto;

import java.time.LocalDateTime;

import com.jadylima.consultacep.entity.ConsultaCep;

public record ConsultaCepResponse(
		String cep,
		String logradouro,
		String bairro,
		String cidade,
		LocalDateTime dataConsulta) {

	public static ConsultaCepResponse from(ConsultaCep consulta) {
		return new ConsultaCepResponse(
				consulta.getCep(),
				consulta.getLogradouro(),
				consulta.getBairro(),
				consulta.getCidade(),
				consulta.getDataConsulta());
	}
}
