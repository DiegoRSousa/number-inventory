package com.minsait.numberinventory.controller.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<CampoErro>> validation(MethodArgumentNotValidException ex) {
		var erros = ex.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(CampoErro::new).toList());
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> validation(ResponseStatusException ex) {
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}
	
	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<String> validation(ObjectOptimisticLockingFailureException  ex) {
		logger.info("Ocorreu um conflito na reserva de número, tente novamnete, {}",ex.getMessage());
		return ResponseEntity.unprocessableEntity().body("Ocorreu um conflito na reserva de número, tente novamnete");
	}
	
	private record CampoErro(String campo, String mensagem) {
		public CampoErro(FieldError fieldError) {
			this(fieldError.getField(), fieldError.getDefaultMessage());
		}
	}
}
