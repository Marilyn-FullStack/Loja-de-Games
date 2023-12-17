package com.generation.loja.controller;

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

import com.generation.loja.model.Categoria;
import com.generation.loja.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	 @Autowired
	    private CategoriaRepository categoriaRepository;

	    @GetMapping
	    public ResponseEntity<List<Categoria>> listarCategorias() {
	        List<Categoria> categorias = categoriaRepository.findAll();
	        return ResponseEntity.ok(categorias);
	    }

	    @PostMapping
	    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
	        Categoria novaCategoria = categoriaRepository.save(categoria);
	        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
	        Categoria categoria = categoriaRepository.findById(id).orElse(null);
	        return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
	        // Verificar se a categoria existe antes de tentar atualizar
	        if (categoriaRepository.existsById(id)) {
	            categoria.setId(id);
	            Categoria categoriaAtualizada = categoriaRepository.save(categoria);
	            return ResponseEntity.ok(categoriaAtualizada);
	        }
	        return ResponseEntity.notFound().build();
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
	        // Verificar se a categoria existe antes de tentar deletar
	        if (categoriaRepository.existsById(id)) {
	            categoriaRepository.deleteById(id);
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.notFound().build();
	    }
	}