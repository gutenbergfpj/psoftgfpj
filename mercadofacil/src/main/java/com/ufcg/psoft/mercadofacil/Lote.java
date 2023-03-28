package com.ufcg.psoft.mercadofacil;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class Lote {

	private Long id;
    private Produto produto;
	private int quantidade;
	
	Lote(Long id, Produto product, int qtt){
		
		this.id = id;
		this.produto = product;
		this.quantidade = qtt;
		
	}

	public Produto getProduto() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int numeroDeItens(int i) {
		// TODO Auto-generated method stub
		return 0;
	}
}
