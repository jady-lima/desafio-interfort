package com.jadylima.consultacep.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jadylima.consultacep.dto.ConsultaCepResponse;
import com.jadylima.consultacep.entity.ConsultaCep;
import com.jadylima.consultacep.integration.viacep.ViaCepException;
import com.jadylima.consultacep.service.CepNaoEncontradoException;
import com.jadylima.consultacep.service.ConsultaCepService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsultaCepController.class)
class ConsultaCepControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ConsultaCepService consultaCepService;

	@Test
	void retornaConsultaComSucesso() throws Exception {
		when(consultaCepService.consultar("01001000")).thenReturn(new ConsultaCep(
				"01001000", "Praça da Sé", "Sé", "São Paulo",
				LocalDateTime.of(2026, 10, 2, 10, 30)));

		mockMvc.perform(get("/consultas").param("cep", "01001000"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cep").value("01001000"))
				.andExpect(jsonPath("$.logradouro").value("Praça da Sé"))
				.andExpect(jsonPath("$.bairro").value("Sé"))
				.andExpect(jsonPath("$.cidade").value("São Paulo"))
				.andExpect(jsonPath("$.dataConsulta").exists())
				.andExpect(jsonPath("$.id").doesNotExist());
	}

	@Test
	void retorna404QuandoCepNaoForEncontrado() throws Exception {
		when(consultaCepService.consultar("99999999"))
				.thenThrow(new CepNaoEncontradoException("99999999"));

		mockMvc.perform(get("/consultas").param("cep", "99999999"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.message").value("CEP não encontrado: 99999999"));
	}

	@Test
	void retorna400QuandoCepForInvalido() throws Exception {
		when(consultaCepService.consultar("123"))
				.thenThrow(new IllegalArgumentException("CEP deve conter exatamente 8 dígitos: 123"));

		mockMvc.perform(get("/consultas").param("cep", "123"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400));
	}

	@Test
	void retorna502QuandoViaCepEstiverIndisponivel() throws Exception {
		when(consultaCepService.consultar("01001000"))
				.thenThrow(new ViaCepException("falha interna", new IllegalStateException()));

		mockMvc.perform(get("/consultas").param("cep", "01001000"))
				.andExpect(status().isBadGateway())
				.andExpect(jsonPath("$.status").value(502))
				.andExpect(jsonPath("$.message").value("Serviço ViaCEP indisponível"));
	}

	@Test
	void retornaHistoricoComoListaDeDtos() throws Exception {
		when(consultaCepService.listarHistorico()).thenReturn(List.of(
				new ConsultaCepResponse("01001000", "Praça da Sé", "Sé", "São Paulo",
						LocalDateTime.of(2026, 10, 2, 10, 30))));

		mockMvc.perform(get("/consultas"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].cep").value("01001000"))
				.andExpect(jsonPath("$[0].logradouro").value("Praça da Sé"))
				.andExpect(jsonPath("$[0].bairro").value("Sé"))
				.andExpect(jsonPath("$[0].cidade").value("São Paulo"))
				.andExpect(jsonPath("$[0].dataConsulta").exists())
				.andExpect(jsonPath("$[0].id").doesNotExist());
	}

	@Test
	void retornaListaVaziaQuandoHistoricoNaoPossuiConsultas() throws Exception {
		when(consultaCepService.listarHistorico()).thenReturn(List.of());

		mockMvc.perform(get("/consultas"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()").value(0));
	}
}
