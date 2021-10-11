package br.com.yuri.loja.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.yuri.loja.dto.InfFornecedorDTO;
import br.com.yuri.loja.dto.InfPedidoDTO;
import br.com.yuri.loja.dto.ItemCompraDTO;

@FeignClient("fornecedor")
public interface FornecedorClient {
	
	@RequestMapping("/info/{estado}")
	InfFornecedorDTO getInfoPorEstado(@PathVariable String estado);
	
	@RequestMapping(method = RequestMethod.POST,value = "/pedido")
	InfPedidoDTO realizarPedido(List<ItemCompraDTO> itens);

}
