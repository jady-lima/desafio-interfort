package com.jadylima.consultacep.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jadylima.consultacep.dto.ConsultaCepResponse;
import com.jadylima.consultacep.service.ConsultaCepService;

@RestController
@RequestMapping("/consultas")
public class ConsultaCepController {

	private final ConsultaCepService consultaCepService;

	public ConsultaCepController(ConsultaCepService consultaCepService) {
		this.consultaCepService = consultaCepService;
	}

	@GetMapping(params = "cep")
	public ResponseEntity<ConsultaCepResponse> consultar(@RequestParam("cep") String cep) {
		return ResponseEntity.ok(ConsultaCepResponse.from(consultaCepService.consultar(cep)));
	}

	@GetMapping(params = "!cep")
	public ResponseEntity<List<ConsultaCepResponse>> listarHistorico() {
		return ResponseEntity.ok(consultaCepService.listarHistorico());
	}
}
