package com.jadylima.consultacep.integration.viacep;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ViaCepResponse(
		String cep,
		String logradouro,
		String bairro,
		String localidade,
		Boolean erro) {

	public boolean cepNaoEncontrado() {
		return Boolean.TRUE.equals(erro);
	}

}
