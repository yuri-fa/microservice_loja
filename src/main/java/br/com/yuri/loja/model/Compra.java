package br.com.yuri.loja.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Compra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long pedidoId;
	private Integer tempoDePreparo;
	private String endereco;
	private LocalDate dataParaEntrega;
	private Long voucher;
	@Enumerated(EnumType.STRING)
	private CompraState state;
	
	public LocalDate getDataParaEntrega() {
		return dataParaEntrega;
	}
	public void setDataParaEntrega(LocalDate dataParaEntrega) {
		this.dataParaEntrega = dataParaEntrega;
	}
	public Long getVoucher() {
		return voucher;
	}
	public void setVoucher(Long voucher) {
		this.voucher = voucher;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTempoDePreparo() {
		return tempoDePreparo;
	}
	public void setTempoDePreparo(Integer tempoDePreparo) {
		this.tempoDePreparo = tempoDePreparo;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Long getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
	public CompraState getState() {
		return state;
	}
	public void setState(CompraState state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Compra [id=" + id + ", pedidoId=" + pedidoId + ", tempoDePreparo=" + tempoDePreparo + ", endereco="
				+ endereco + ", dataParaEntrega=" + dataParaEntrega + ", voucher=" + voucher + ", state=" + state + "]";
	}
	
	

}
