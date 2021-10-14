package br.com.yuri.loja.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.yuri.loja.client.FornecedorClient;
import br.com.yuri.loja.dto.CompraDTO;
import br.com.yuri.loja.dto.InfFornecedorDTO;
import br.com.yuri.loja.dto.InfPedidoDTO;
import br.com.yuri.loja.model.Compra;
import br.com.yuri.loja.repository.CompraRepository;

@Service
public class CompraService {

	private final Logger LOG = LoggerFactory.getLogger(CompraService.class);

	@Autowired
	private FornecedorClient fornecedorClient;
	
	@Autowired
	private CompraRepository compraRepository;
	
	@HystrixCommand(threadPoolKey = "getByIdThreadPool")
	public Compra getById(Long id) {
		return compraRepository.findById(id).orElse(new Compra());
	}
	
	@HystrixCommand(fallbackMethod = "realizarCompraFallback",threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDTO compraDTO) {
		LOG.info("Consultando Fornecedor ",compraDTO.getEndereco().getEstado().toUpperCase());
		InfFornecedorDTO inf = fornecedorClient.getInfoPorEstado(compraDTO.getEndereco().getEstado().toUpperCase());
		LOG.info("Criando um pedido");
		InfPedidoDTO pedido = fornecedorClient.realizarPedido(compraDTO.getItensList());
		
		Compra compra = new Compra();
		compra.setTempoDePreparo(2);
		compra.setEndereco(compraDTO.getEndereco().toString());
		System.out.println(compra.getEndereco());
		LOG.info("Endereço : "+ compra.getEndereco());
		LOG.info("Finalizando pedido : ", compra.getId());
		compraRepository.save(compra);
		return compra;
	}
	
	public Compra realizarCompraFallback(CompraDTO compraDTO) {
		Compra compra = new Compra();
		compra.setEndereco(compraDTO.getEndereco().toString());
		return compra;
	}
}
