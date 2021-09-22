package br.com.yuri.loja.dto;

import java.util.List;

public class CompraDTO {
	
	private List<ItemCompraDTO> itensList;
	
	private EnderecoDTO endereco;

	public List<ItemCompraDTO> getItensList() {
		return itensList;
	}

	public void setItensList(List<ItemCompraDTO> itensList) {
		this.itensList = itensList;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

}
