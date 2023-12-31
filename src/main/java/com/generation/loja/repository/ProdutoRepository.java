package com.generation.loja.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.loja.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>  {
	public List <Produto> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
	List<Produto> findByPrecoLessThan(BigDecimal valor);
	List<Produto> findByPrecoGreaterThan(BigDecimal valor);
}
