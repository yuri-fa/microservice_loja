package br.com.yuri.loja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.yuri.loja.dto.InfEntregaDTO;
import br.com.yuri.loja.dto.InfVoucherDTO;


@FeignClient("transportador")
public interface TransportadorClient {

	@RequestMapping(value = "/entrega", method = RequestMethod.POST)
	public InfVoucherDTO reservaEntrega(InfEntregaDTO infEntregaDTO);
}
