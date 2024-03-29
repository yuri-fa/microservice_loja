package br.com.yuri.loja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.yuri.loja.dto.CompraDTO;
import br.com.yuri.loja.model.Compra;
import br.com.yuri.loja.service.CompraService;

@RestController
@RequestMapping("/compra")
public class CompraController {

	@Autowired
	private CompraService compraService;
	
	@PostMapping
	public Compra realizarCompra(@RequestBody CompraDTO compraDTO) {
		return compraService.realizaCompra(compraDTO);
	}
	
	@GetMapping(value = "/{id}")
	public Compra getById(@PathVariable(name = "id") Long id) {
		return compraService.getById(id);
	}
	
}
