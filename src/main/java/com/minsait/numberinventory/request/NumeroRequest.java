package com.minsait.numberinventory.request;

import com.minsait.numberinventory.model.Numero;

import jakarta.validation.constraints.NotBlank;

public record NumeroRequest(@NotBlank String ddd, @NotBlank String prefixo, @NotBlank String sufixo, @NotBlank String codigo) {

	public Numero toModel() {
		return new Numero(ddd, prefixo, sufixo, codigo);
	}

}
