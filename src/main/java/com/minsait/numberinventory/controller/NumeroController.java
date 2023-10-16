package com.minsait.numberinventory.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.numberinventory.model.Numero;
import com.minsait.numberinventory.repository.NumeroRepository;
import com.minsait.numberinventory.request.NumeroRequest;
import com.minsait.numberinventory.response.NumeroResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("numero")
public class NumeroController {
	
	private static final Logger logger = LoggerFactory.getLogger(NumeroController.class);
	
	@Autowired
	private final NumeroRepository numeroRepository;
	
	private final RedisTemplate<String, Numero> template;
	private final ChannelTopic topic;
	

	public NumeroController(NumeroRepository numeroRepository, RedisTemplate<String, Numero> template, ChannelTopic topic) {
		this.numeroRepository = numeroRepository;
		this.template = template;
		this.topic = topic;
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@Valid @RequestBody NumeroRequest request) {
		
		var numero = request.toModel();
		logger.info("adicionado numero a fila: {}", numero);
		
		template.convertAndSend(topic.getTopic(), numero);
		
		
		
		//numeroRepository.save(numero);
		
		//return ResponseEntity.created(URI.create("/numero/"+1)).build();
		
		return ResponseEntity.accepted().header("Location", "/numero/"+numero.getUuid()).build();		
	}
	
	@GetMapping("/{codigo}")
	public List<NumeroResponse> listarPorCodigo(@PathVariable String codigo) {
		logger.info("buscando numeros pelo código: {}", codigo);
		var numeros = numeroRepository.findByCodigo(codigo);
		return numeros.stream().map(NumeroResponse::new).toList();
	}
	
	@GetMapping
	@Cacheable("listarPorParteDoNumero")
	public List<NumeroResponse> listarPorParteDoNumero(@RequestParam String parteDoNumero) {
		logger.info("buscando numeros por parte do numero: {}", parteDoNumero);
		var numeros = numeroRepository.buscarPorParteDoNumero(parteDoNumero, PageRequest.of(0, 50));
		return numeros.stream().map(NumeroResponse::new).toList();
	}
	
}
