package br.com.yuri.loja.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.yuri.loja.client.FornecedorClient;
import br.com.yuri.loja.client.TransportadorClient;
import br.com.yuri.loja.dto.CompraDTO;
import br.com.yuri.loja.dto.InfEntregaDTO;
import br.com.yuri.loja.dto.InfFornecedorDTO;
import br.com.yuri.loja.dto.InfPedidoDTO;
import br.com.yuri.loja.dto.InfVoucherDTO;
import br.com.yuri.loja.model.Compra;
import br.com.yuri.loja.model.CompraState;
import br.com.yuri.loja.repository.CompraRepository;

@Service
public class CompraService {

	private final Logger LOG = LoggerFactory.getLogger(CompraService.class);

	@Autowired
	private FornecedorClient fornecedorClient;

	@Autowired
	private TransportadorClient transportadorClient;
	
	@Autowired
	private CompraRepository compraRepository;
	
	@HystrixCommand(threadPoolKey = "getByIdThreadPool")
	public Compra getById(Long id) {
		return compraRepository.findById(id).orElse(new Compra());
	}
	
	@HystrixCommand(fallbackMethod = "realizarCompraFallback",threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDTO compraDTO) {
		
		LOG.info("Iniciando processo de compra : ");
		Compra compra = new Compra();
		compra.setState(CompraState.RECEBIDO);
		compraRepository.save(compra);
		LOG.info("Compra salva : ", compra.toString());
		
		LOG.info("Consultando Fornecedor ",compraDTO.getEndereco().getEstado().toUpperCase());
		InfFornecedorDTO infFornecedor = fornecedorClient.getInfoPorEstado(compraDTO.getEndereco().getEstado().toUpperCase());
		
		LOG.info("Criando um pedido");
		InfPedidoDTO pedido = fornecedorClient.realizarPedido(compraDTO.getItensList());
		compra.setState(CompraState.PEDIDO_REALIZADO);
		compra.setPedidoId(pedido.getId());
		compra.setTempoDePreparo(pedido.getTempoDePreparo());
		compraRepository.save(compra);
		LOG.info("Dados do pedido salvo na compra: ", compra.toString());
		
		LOG.info("Criando um Voucher de entrega");
		InfEntregaDTO infEntregaDTO = new InfEntregaDTO();
		infEntregaDTO.setPedidoId(pedido.getId());
		infEntregaDTO.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));
		infEntregaDTO.setEnderecoOrigem(infFornecedor.getEndereco());
		infEntregaDTO.setEnderecoDestino(compraDTO.getEndereco().toString());
		InfVoucherDTO infVouchDTO = transportadorClient.reservaEntrega(infEntregaDTO);
		
		compra.setState(CompraState.RESERVA_ENTREGA_REALIZADA);
		compra.setTempoDePreparo(2);
		compra.setEndereco(compraDTO.getEndereco().toString());
		compra.setDataParaEntrega(infVouchDTO.getPrevisaoParaEntrega());
		compra.setVoucher(infVouchDTO.getNumero());
		compraRepository.save(compra);
		
		LOG.info("Finalizando pedido : ", compra.getId());
		return compra;
	}
	
	public Compra realizarCompraFallback(CompraDTO compraDTO) {
		Compra compra = new Compra();
		compra.setEndereco(compraDTO.getEndereco().toString());
		return compra;
	}
}
