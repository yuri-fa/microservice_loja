package br.com.yuri.loja.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.yuri.loja.dto.CompraDTO;
import br.com.yuri.loja.dto.InfFornecedorDTO;

@Service
public class CompraService {

	public void realizaCompra(CompraDTO compraDTO) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<InfFornecedorDTO> exchange = restTemplate.exchange("http://fornecedor/info/" + compraDTO.getEndereco().getEstado().toUpperCase(),HttpMethod.GET,null,InfFornecedorDTO.class);
		System.out.println(exchange.getBody().getEndereco());
	}
}
