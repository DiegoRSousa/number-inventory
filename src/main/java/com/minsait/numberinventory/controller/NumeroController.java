package com.minsait.numberinventory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.numberinventory.repository.NumeroRepository;
import com.minsait.numberinventory.response.NumeroResponse;

@RestController
@RequestMapping("numero")
public class NumeroController {
	
	private final NumeroRepository numeroRepository;

	public NumeroController(NumeroRepository numeroRepository) {
		this.numeroRepository = numeroRepository;
	}
	
	@GetMapping("/{codigo}")
	public List<NumeroResponse> listarPorCodigo(@PathVariable String codigo) {
		var numeros = numeroRepository.findByCodigo(codigo);
		return numeros.stream().map(NumeroResponse::new).toList();
	}
	

}
