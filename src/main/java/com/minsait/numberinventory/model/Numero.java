package com.minsait.numberinventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity
public class Numero {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ddd;
	private String prefixo;
	private String sufixo;
	private String codigo;
	@Enumerated(EnumType.STRING)
	private StatusNumero status;
	@ManyToOne
	private Reserva reserva;
	@Version
	private Long versao;
	
	public void reservar(Reserva reserva) {
		this.status = StatusNumero.RESERVADO;
		this.reserva = reserva;
	}
	
	public Long getId() {
		return id;
	}
	public String getDdd() {
		return ddd;
	}
	public String getPrefixo() {
		return prefixo;
	}
	public String getSufixo() {
		return sufixo;
	}
	public String getCodigo() {
		return codigo;
	}
	public StatusNumero getStatus() {
		return status;
	}
	public Reserva getReserva() {
		return reserva;
	}

	public Long getVersao() {
		return versao;
	}
}
