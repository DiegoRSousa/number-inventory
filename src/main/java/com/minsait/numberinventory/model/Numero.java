package com.minsait.numberinventory.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Numero implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private UUID uuid;
	private String ddd;
	private String prefixo;
	private String sufixo;
	private String codigo;
	private String numeroCompleto;
	@Enumerated(EnumType.STRING)
	private StatusNumero status;
	@ManyToOne
	private Reserva reserva;
	
	public Numero() {}
	
	public Numero(String ddd, String prefixo, String sufixo, String codigo) {
		this.uuid = UUID.randomUUID();
		this.ddd = ddd;
		this.prefixo = prefixo;
		this.sufixo = sufixo;
		this.codigo = codigo;
		this.numeroCompleto = ddd+prefixo+sufixo; 
		this.status = StatusNumero.DISPONIVEL;
	}

	public void reservar(Reserva reserva) {
		this.status = StatusNumero.RESERVADO;
		this.reserva = reserva;
	}
	
	public Long getId() {
		return id;
	}
	public UUID getUuid() {
		return uuid;
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
	
	public String getNumeroCompleto() {
		return numeroCompleto;
	}

	public StatusNumero getStatus() {
		return status;
	}
	public Reserva getReserva() {
		return reserva;
	}

//	@Override
//	public String toString() {
//		return "Numero [id=" + id + ", ddd=" + ddd + ", prefixo=" + prefixo + ", sufixo=" + sufixo + ", codigo="
//				+ codigo + ", status=" + status + ", reserva=" + reserva + "]";
//	}
	
}
