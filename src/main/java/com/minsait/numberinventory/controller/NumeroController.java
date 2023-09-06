package com.minsait.numberinventory.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.numberinventory.repository.NumeroRepository;
import com.minsait.numberinventory.request.NumeroRequest;
import com.minsait.numberinventory.response.NumeroResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("numero")
public class NumeroController {
	
	private final NumeroRepository numeroRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(NumeroController.class);

	public NumeroController(NumeroRepository numeroRepository) {
		this.numeroRepository = numeroRepository;
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody NumeroRequest request) {
		var numero = request.toModel();
		logger.info("salvando numero: {}", numero);
		numeroRepository.save(numero);
		return ResponseEntity.created(URI.create("/numero/"+numero.getId())).build();
	}
	
	@GetMapping("/{codigo}")
	public List<NumeroResponse> listarPorCodigo(@PathVariable String codigo) {
		logger.info("buscando numeros pelo código: {}", codigo);
		var numeros = numeroRepository.findByCodigo(codigo);
		return numeros.stream().map(NumeroResponse::new).toList();
	}
	
	@GetMapping
	public List<NumeroResponse> listarPorParteDoNumero(@RequestParam String parteDoNumero) {
		logger.info("buscando numeros por parte do numero: {}", parteDoNumero);
		var numeros = numeroRepository.buscarPorParteDoNumero(parteDoNumero);
		return numeros.stream().map(NumeroResponse::new).toList();
	}
	
}
