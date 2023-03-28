package com.ufcg.psoft.mercadofacil;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class Produto {
	
	private Long id;
	private String nome;
    private double preco;
    private String codigoBarra;
    private String fabricante;
	
	Produto(Long id, String nome, double price, String codigoBarras, String fabricante){
		this.id = id;
		this.nome = nome;
		this.preco = price;
		this.codigoBarra = codigoBarras;
		this.fabricante = fabricante;
	}
	
	public Long getId() {
		//TODO
		return this.id;
	}
    
}
