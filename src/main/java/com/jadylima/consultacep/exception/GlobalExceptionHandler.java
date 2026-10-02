package com.jadylima.consultacep.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jadylima.consultacep.integration.viacep.ViaCepException;
import com.jadylima.consultacep.service.CepNaoEncontradoException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException exception) {
		return error(HttpStatus.BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Map<String, Object>> handleMissingParameter() {
		return error(HttpStatus.BAD_REQUEST, "Parâmetro cep é obrigatório");
	}

	@ExceptionHandler(CepNaoEncontradoException.class)
	public ResponseEntity<Map<String, Object>> handleCepNaoEncontrado(CepNaoEncontradoException exception) {
		return error(HttpStatus.NOT_FOUND, exception.getMessage());
	}

	@ExceptionHandler(ViaCepException.class)
	public ResponseEntity<Map<String, Object>> handleViaCep(ViaCepException exception) {
		return error(HttpStatus.BAD_GATEWAY, "Serviço ViaCEP indisponível");
	}

	private ResponseEntity<Map<String, Object>> error(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(Map.of(
				"status", status.value(),
				"message", message));
	}
}
