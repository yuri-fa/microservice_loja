package br.com.yuri.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yuri.loja.client.FornecedorClient;
import br.com.yuri.loja.dto.CompraDTO;
import br.com.yuri.loja.dto.InfFornecedorDTO;

@Service
public class CompraService {

	@Autowired
	private FornecedorClient fornecedorClient;
	
	public void realizaCompra(CompraDTO compraDTO) {
		InfFornecedorDTO inf = fornecedorClient.getInfoPorEstado(compraDTO.getEndereco().getEstado().toUpperCase());
		System.out.println(inf.getEndereco());
	}
}
