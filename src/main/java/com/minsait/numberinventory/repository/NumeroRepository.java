package com.minsait.numberinventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.minsait.numberinventory.model.Numero;

public interface NumeroRepository extends JpaRepository<Numero, Long>{
	
	@Query(value = "select * from numero n where n.status = 'DISPONIVEL' and n.codigo = :codigo limit :quantidade", nativeQuery = true)
	List<Numero> buscarPorCodigoEQuantidade(@Param("codigo") String codigo, @Param("quantidade")  int quantidade);

	List<Numero> findByCodigo(String codigo);

}
