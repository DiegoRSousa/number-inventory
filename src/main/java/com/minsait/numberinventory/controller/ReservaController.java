package com.minsait.numberinventory.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.minsait.numberinventory.repository.NumeroRepository;
import com.minsait.numberinventory.repository.ReservaRepository;
import com.minsait.numberinventory.request.ReservaRequest;
import com.minsait.numberinventory.response.ReservaResponse;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("reserva")
public class ReservaController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);
	
	private final NumeroRepository numeroRepository;
	private final ReservaRepository reservaRepository;
	
	public ReservaController(NumeroRepository numeroRepository, ReservaRepository reservaRepository) {
		this.numeroRepository = numeroRepository;
		this.reservaRepository = reservaRepository;
	}

	@PostMapping
	@Transactional
	public ReservaResponse novaReserva(@RequestBody @Valid ReservaRequest request) {
		var cpf = request.cpf();
		logger.info("Buscando numeros para realizar reserva para o cpf: {}", cpf);
		
		var numeros = numeroRepository.buscarPorCodigoEQuantidade(request.codigo(), request.quantidade());
		
		logger.info("numeros encontrados: {} para o cpf: {}", numeros, cpf);
		
		sleep(5000);
		var quantidadeDisponivel = numeros.size(); 
		if(quantidadeDisponivel < request.quantidade())
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, 
					"Não há números disponíveis! Quantidade de números disponíveis: " + quantidadeDisponivel);
		var reserva = request.toModel(numeros);
		
		
		logger.info("salvando reserva: {}", reserva);
		reservaRepository.save(reserva);
		numeros.forEach(n -> n.reservar(reserva));
		numeroRepository.saveAll(numeros);
		
		return new ReservaResponse(reserva);
	}
	
	@GetMapping
	public List<ReservaResponse> listar(){
		var reservas = reservaRepository.findAll();
		return reservas.stream().map(ReservaResponse::new).toList();
	}
	
	private void sleep(int tempo) {
		try {
			Thread.sleep(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
