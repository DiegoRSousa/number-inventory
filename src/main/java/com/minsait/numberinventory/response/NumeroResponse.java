package com.minsait.numberinventory.response;

import com.minsait.numberinventory.model.Numero;
import com.minsait.numberinventory.model.StatusNumero;

public record NumeroResponse(Long id, String ddd, String prefixo, String sufixo, String codigo, StatusNumero status) {
	
	public NumeroResponse(Numero numero) {
		this(numero.getId(), numero.getDdd(), numero.getPrefixo(), numero.getSufixo(), numero.getCodigo(), numero.getStatus());
	}

}
