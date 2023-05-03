package com.minsait.numberinventory.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Reserva {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String cpf;
	@NotNull
	@Size(min = 1)
	@OneToMany
	@JoinColumn(name = "reserva_id")
	private List<Numero> numeros;
	
	public Reserva() {}
	
	public Reserva(String cpf, List<Numero> numeros) {
		this.cpf = cpf;
		this.numeros = numeros;
	}

	public int quantidadeDeNumero() {
		return numeros.size();
	}
	
	public Long getId() {
		return id;
	}
	public String getCpf() {
		return cpf;
	}
	public List<Numero> getNumeros() {
		return numeros;
	}
}
