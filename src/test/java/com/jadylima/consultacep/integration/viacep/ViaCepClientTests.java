package com.jadylima.consultacep.integration.viacep;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class ViaCepClientTests {

	private static final String BASE_URL = "https://viacep.test/ws";

	private MockRestServiceServer servidor;
	private ViaCepClient client;

	@BeforeEach
	void configurar() {
		RestClient.Builder builder = RestClient.builder().baseUrl(BASE_URL);
		servidor = MockRestServiceServer.bindTo(builder).build();
		client = new ViaCepClient(builder.build());
	}

	@Test
	void converteRespostaDeCepEncontrado() {
		String json = """
				{
				  "cep": "01001-000",
				  "logradouro": "Praça da Sé",
				  "complemento": "lado ímpar",
				  "unidade": "",
				  "bairro": "Sé",
				  "localidade": "São Paulo",
				  "uf": "SP",
				  "estado": "São Paulo",
				  "regiao": "Sudeste",
				  "ibge": "3550308",
				  "gia": "1004",
				  "ddd": "11",
				  "siafi": "7107"
				}
				""";
		servidor.expect(requestTo(BASE_URL + "/01001000/json/"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

		Optional<ViaCepResponse> resposta = client.buscarEndereco("01001000");

		assertThat(resposta).hasValueSatisfying(endereco -> {
			assertThat(endereco.cep()).isEqualTo("01001-000");
			assertThat(endereco.logradouro()).isEqualTo("Praça da Sé");
			assertThat(endereco.bairro()).isEqualTo("Sé");
			assertThat(endereco.localidade()).isEqualTo("São Paulo");
		});
		servidor.verify();
	}

	@ParameterizedTest
	@ValueSource(strings = { "{\"erro\": \"true\"}", "{\"erro\": true}" })
	void retornaVazioQuandoCepNaoExiste(String json) {
		servidor.expect(requestTo(BASE_URL + "/99999999/json/")).andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

		Optional<ViaCepResponse> resposta = client.buscarEndereco("99999999");

		assertThat(resposta).isEmpty();
		servidor.verify();
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "1234567", "123456789", "01001-000", "abcdefgh" })
	void rejeitaCepComFormatoInvalidoSemChamarViaCep(String cep) {
		assertThatThrownBy(() -> client.buscarEndereco(cep)).isInstanceOf(IllegalArgumentException.class);
		servidor.verify();
	}

	@Test
	void rejeitaCepNulo() {
		assertThatThrownBy(() -> client.buscarEndereco(null)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void lancaViaCepExceptionQuandoViaCepFalha() {
		servidor.expect(requestTo(BASE_URL + "/01001000/json/")).andRespond(withServerError());

		assertThatThrownBy(() -> client.buscarEndereco("01001000")).isInstanceOf(ViaCepException.class);
		servidor.verify();
	}

}
