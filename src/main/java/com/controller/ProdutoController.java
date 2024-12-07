package com.controller;

import java.util.List;
import java.util.Optional;

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

import com.model.Produto;
import com.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RequestMapping("/produtos")
@RestController
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(produtoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		return produtoRepository.findById(id).map(response -> ResponseEntity.ok(response))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping()
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
	}

	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
		Optional<Produto> prod =  produtoRepository.findById(produto.getId());
		
		if(prod.isEmpty()) 
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(produtoRepository.save(produto));
	}

	@GetMapping("frete/{frete}")
	public ResponseEntity<List<Produto>> getByEntrega(@PathVariable Float frete) {
		return ResponseEntity.ok(produtoRepository.findAllByEntrega(frete));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);

		if (produto.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este ID não existe.");

		produtoRepository.deleteById(id);
		return ResponseEntity.ok("Deletado Com Sucesso!");

	}

}
