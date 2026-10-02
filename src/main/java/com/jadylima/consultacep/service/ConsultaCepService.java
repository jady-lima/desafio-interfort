package com.jadylima.consultacep.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jadylima.consultacep.dto.ConsultaCepResponse;
import com.jadylima.consultacep.entity.ConsultaCep;
import com.jadylima.consultacep.integration.viacep.ViaCepClient;
import com.jadylima.consultacep.integration.viacep.ViaCepResponse;
import com.jadylima.consultacep.repository.ConsultaCepRepository;

@Service
public class ConsultaCepService {

	private final ViaCepClient viaCepClient;
	private final ConsultaCepRepository repository;

	public ConsultaCepService(ViaCepClient viaCepClient, ConsultaCepRepository repository) {
		this.viaCepClient = viaCepClient;
		this.repository = repository;
	}

	public ConsultaCep consultar(String cep) {
		ViaCepResponse endereco = viaCepClient.buscarEndereco(cep).orElseThrow(() -> new CepNaoEncontradoException(cep));

		ConsultaCep consulta = new ConsultaCep(
				normalizarCep(endereco.cep()),
				endereco.logradouro(),
				endereco.bairro(),
				endereco.localidade(),
				LocalDateTime.now()
			);

		return repository.save(consulta);
	}

	public List<ConsultaCepResponse> listarHistorico() {
		return repository.findAll().stream()
				.map(ConsultaCepResponse::from)
				.toList();
	}

	private String normalizarCep(String cep) {
		return cep.replace("-", "");
	}

}
