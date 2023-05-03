package com.minsait.numberinventory.request;

import java.util.List;

import com.minsait.numberinventory.model.Numero;
import com.minsait.numberinventory.model.Reserva;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReservaRequest (@NotBlank String cpf, @NotBlank String codigo, @NotNull @Positive int quantidade){

	public Reserva toModel(List<Numero> numeros) {
		return new Reserva(cpf, numeros);
	}

}
