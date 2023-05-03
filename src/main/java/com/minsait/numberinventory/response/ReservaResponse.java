package com.minsait.numberinventory.response;

import java.util.List;

import com.minsait.numberinventory.model.Reserva;

public record ReservaResponse(Long id, String cpf, List<NumeroResponse> numeros) {

	public ReservaResponse(Reserva reserva) {
		this(reserva.getId(), reserva.getCpf(), reserva.getNumeros().stream().map(n -> new NumeroResponse(
				n.getId(), n.getDdd(), n.getPrefixo(), n.getSufixo(), n.getCodigo(), n.getStatus())).toList());
	}
}