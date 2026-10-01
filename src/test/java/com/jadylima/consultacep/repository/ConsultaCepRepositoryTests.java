package com.jadylima.consultacep.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.jadylima.consultacep.entity.ConsultaCep;

@DataJpaTest
class ConsultaCepRepositoryTests {

	@Autowired
	private ConsultaCepRepository repository;

	@Test
	void salvaERecuperaConsulta() {
		LocalDateTime dataConsulta = LocalDateTime.of(2026, 9, 30, 10, 30);
		ConsultaCep consulta = new ConsultaCep("01001000", "Praça da Sé", "Sé", "São Paulo", dataConsulta);

		ConsultaCep salva = repository.saveAndFlush(consulta);

		assertThat(salva.getId()).isNotNull();
		assertThat(repository.findById(salva.getId()))
				.get()
				.satisfies(encontrada -> {
					assertThat(encontrada.getCep()).isEqualTo("01001000");
					assertThat(encontrada.getLogradouro()).isEqualTo("Praça da Sé");
					assertThat(encontrada.getBairro()).isEqualTo("Sé");
					assertThat(encontrada.getCidade()).isEqualTo("São Paulo");
					assertThat(encontrada.getDataConsulta()).isEqualTo(dataConsulta);
				});
	}

}
