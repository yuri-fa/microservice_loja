package br.com.yuri.loja.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CompraDTO {
	
	private List<ItemCompraDTO> itensList;
	
	private EnderecoDTO endereco;
	
	@JsonIgnore
	private Long compraID;

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

	public Long getCompraID() {
		return compraID;
	}

	public void setCompraID(Long compraID) {
		this.compraID = compraID;
	}
	
}
