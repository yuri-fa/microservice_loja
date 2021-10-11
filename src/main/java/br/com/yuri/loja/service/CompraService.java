package br.com.yuri.loja.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yuri.loja.client.FornecedorClient;
import br.com.yuri.loja.dto.CompraDTO;
import br.com.yuri.loja.dto.InfFornecedorDTO;
import br.com.yuri.loja.dto.InfPedidoDTO;
import br.com.yuri.loja.model.Compra;

@Service
public class CompraService {

	private final Logger LOG = LoggerFactory.getLogger(CompraService.class);

	@Autowired
	private FornecedorClient fornecedorClient;
	
	public Compra realizaCompra(CompraDTO compraDTO) {
		LOG.info("Consultando Fornecedor ",compraDTO.getEndereco().getEstado().toUpperCase());
		InfFornecedorDTO inf = fornecedorClient.getInfoPorEstado(compraDTO.getEndereco().getEstado().toUpperCase());
		LOG.info("Criando um pedido");
		InfPedidoDTO pedido = fornecedorClient.realizarPedido(compraDTO.getItensList());
		
		Compra compra = new Compra();
		compra.setId(pedido.getId());
		compra.setTempoDePreparo(2);
		compra.setEndereco(compraDTO.getEndereco().toString());
		LOG.info("Finalizando pedido : ", compra.getId());
		return compra;
	}
}
