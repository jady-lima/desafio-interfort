package com.jadylima.consultacep.integration.viacep;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class ViaCepClient {

	private static final Pattern FORMATO_CEP = Pattern.compile("\\d{8}");
	private final RestClient restClient;

	public ViaCepClient(RestClient viaCepRestClient) {
		this.restClient = viaCepRestClient;
	}

	public Optional<ViaCepResponse> buscarEndereco(String cep) {
		if (cep == null || !FORMATO_CEP.matcher(cep).matches()) {
			throw new IllegalArgumentException("CEP deve conter exatamente 8 dígitos: " + cep);
		}

		ViaCepResponse resposta;
		try {
			resposta = restClient.get()
					.uri("/{cep}/json/", cep)
					.retrieve()
					.body(ViaCepResponse.class);
		} catch (RestClientException e) {
			throw new ViaCepException("Falha ao consultar o CEP " + cep + " na ViaCEP", e);
		}

		if (resposta == null || resposta.cepNaoEncontrado()) {
			return Optional.empty();
		}

		return Optional.of(resposta);
	}

}
