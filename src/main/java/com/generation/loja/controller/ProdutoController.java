package com.generation.loja.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.loja.model.Produto;
import com.generation.loja.repository.CategoriaRepository;
import com.generation.loja.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Produto>> getAllProdutos() {
		List<Produto> produtos = produtoRepository.findAll();
		return ResponseEntity.ok(produtos);
	}

	@PostMapping
	public ResponseEntity<Produto> criarProduto(@Valid @RequestBody Produto produto) {
		// Verificar se a categoria associada ao produto existe
		if (categoriaRepository.existsById(produto.getCategoria().getId())) {
			Produto novoProduto = produtoRepository.save(produto);
			return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe.");
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
		Produto produto = produtoRepository.findById(id).orElse(null);
		return produto != null ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @Valid @RequestBody Produto produto) {
		// Verificar se o produto existe antes de tentar atualizar
		if (produtoRepository.existsById(id)) {
			produto.setId(id);
			Produto produtoAtualizado = produtoRepository.save(produto);
			return ResponseEntity.ok(produtoAtualizado);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
		// Verificar se o produto existe antes de tentar deletar
		if (produtoRepository.existsById(id)) {
			produtoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/maior-que/{valor}")
	public List<Produto> listarProdutosMaiorQue(@PathVariable BigDecimal valor) {
		return produtoRepository.findByPrecoGreaterThan(valor);
	}

	@GetMapping("/menor-que/{valor}")
	public List<Produto> listarProdutosMenorQue(@PathVariable BigDecimal valor) {
		return produtoRepository.findByPrecoLessThan(valor);
	}

	@GetMapping("/contendo/{nome}")
	public List<Produto> buscarProdutosPorNomeContendoIgnoreCase(@PathVariable String nome) {
		return produtoRepository.findAllByNomeContainingIgnoreCase(nome);
	}
}
