package com.jadylima.consultacep.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.jadylima.consultacep.entity.ConsultaCep;
import com.jadylima.consultacep.integration.viacep.ViaCepClient;
import com.jadylima.consultacep.integration.viacep.ViaCepException;
import com.jadylima.consultacep.integration.viacep.ViaCepResponse;
import com.jadylima.consultacep.repository.ConsultaCepRepository;

@ExtendWith(MockitoExtension.class)
class ConsultaCepServiceTests {

	@Mock
	private ViaCepClient viaCepClient;

	@Mock
	private ConsultaCepRepository repository;

	@InjectMocks
	private ConsultaCepService service;

	@Test
	void salvaERetornaConsultaQuandoCepEncontrado() {
		ViaCepResponse resposta = new ViaCepResponse("01001-000", "Praça da Sé", "Sé", "São Paulo", null);
		when(viaCepClient.buscarEndereco("01001000")).thenReturn(Optional.of(resposta));
		when(repository.save(any(ConsultaCep.class))).thenAnswer(invocacao -> {
			ConsultaCep consulta = invocacao.getArgument(0);
			ReflectionTestUtils.setField(consulta, "id", 1L);
			return consulta;
		});

		LocalDateTime antes = LocalDateTime.now();
		ConsultaCep resultado = service.consultar("01001000");
		LocalDateTime depois = LocalDateTime.now();

		ArgumentCaptor<ConsultaCep> captor = ArgumentCaptor.forClass(ConsultaCep.class);
		verify(repository).save(captor.capture());
		ConsultaCep salva = captor.getValue();

		assertThat(salva.getCep()).isEqualTo("01001000");
		assertThat(salva.getLogradouro()).isEqualTo("Praça da Sé");
		assertThat(salva.getBairro()).isEqualTo("Sé");
		assertThat(salva.getCidade()).isEqualTo("São Paulo");
		assertThat(salva.getDataConsulta()).isBetween(antes, depois);

		assertThat(resultado).isSameAs(salva);
		assertThat(resultado.getId()).isEqualTo(1L);
	}

	@Test
	void lancaExcecaoESemSalvarQuandoCepNaoEncontrado() {
		when(viaCepClient.buscarEndereco("99999999")).thenReturn(Optional.empty());

		assertThatThrownBy(() -> service.consultar("99999999"))
				.isInstanceOf(CepNaoEncontradoException.class)
				.hasMessageContaining("99999999");

		verify(repository, never()).save(any());
	}

	@Test
	void propagaFalhaDaViaCepSemSalvar() {
		when(viaCepClient.buscarEndereco("01001000"))
				.thenThrow(new ViaCepException("ViaCEP indisponível", null));

		assertThatThrownBy(() -> service.consultar("01001000"))
				.isInstanceOf(ViaCepException.class);

		verify(repository, never()).save(any());
	}

}
