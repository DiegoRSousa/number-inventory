package com.minsait.numberinventory.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.numberinventory.model.Numero;
import com.minsait.numberinventory.repository.NumeroRepository;

@Service
public class Reciever implements MessageListener{
	
	private static final Logger logger = LoggerFactory.getLogger(Reciever.class);
	
	
	private final NumeroRepository numeroRepository;	

	public Reciever(NumeroRepository numeroRepository) {
		this.numeroRepository = numeroRepository;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		logger.info("Consumindo evento: {}", message);
		try {
			
			var mapper = new ObjectMapper();
			var numero = mapper.readValue(message.toString(), Numero.class);
			
			 Teste.numeros.add(numero);
			logger.info("numeros tamanho {}", Teste.numeros.size());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Scheduled(fixedDelay = 2000)
	public void verifica() {
		
		logger.info("persistindo {} numeros", Teste.numeros.size());
		numeroRepository.saveAll(Teste.numeros);
		Teste.numeros.clear();
	}

}
