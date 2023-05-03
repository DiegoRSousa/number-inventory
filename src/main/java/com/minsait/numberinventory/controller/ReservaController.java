package com.minsait.numberinventory.controller;


import java.util.List;

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
	
	private final NumeroRepository numeroRepository;
	private final ReservaRepository reservaRepository;
	
	public ReservaController(NumeroRepository numeroRepository, ReservaRepository reservaRepository) {
		this.numeroRepository = numeroRepository;
		this.reservaRepository = reservaRepository;
	}

	@PostMapping
	@Transactional
	public ReservaResponse novaReserva(@RequestBody @Valid ReservaRequest request) {
		var numeros = numeroRepository.buscarPorCodigoEQuantidade(request.codigo(), request.quantidade());
		
		sleep(5000);
		var quantidadeDisponivel = numeros.size(); 
		if(quantidadeDisponivel < request.quantidade())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"Não há números disponíveis! Quantidade de números disponíveis: " + quantidadeDisponivel);
		var reserva = request.toModel(numeros);
		
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
