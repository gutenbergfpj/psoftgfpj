package com.ufcg.psoft.mercadofacil;

import java.util.HashMap;
import java.util.Map;
//import java.util.NoSuchElementException;


public class MercadoFacilRepository{

    private Map<Produto,Long> productRepository = new HashMap<Produto,Long>();
	
	void createProduct(Long id, String name, double price, String codigoBarras, String fabricante) {
		Produto prod = new Produto(id,name,price, codigoBarras, fabricante);
		productRepository.put(prod,id);
	}
	
//	Produto recoverProduct(Long prodId) {
//		if (productRepository.containsValue(prodId)) {
//			return productRepository.get(prodId);
//		} else {
//			throw new NoSuchElementException();
//		}
//	}
	
	void updateProduct(Produto p) {
		
	}
	
	void deleteProduct(Produto p) {
		productRepository.remove(p.getId());
	}
}