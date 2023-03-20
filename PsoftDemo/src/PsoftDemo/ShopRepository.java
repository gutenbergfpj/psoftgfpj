package PsoftDemo;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ShopRepository {

	private Map<Integer,Produto> productRepository = new HashMap<Integer,Produto>();
	
	void createProduct(String name, String brand, double price) {
		Produto prod = new Produto(name,brand,price);
		productRepository.put(prod.getId(), prod);
	}
	
	Produto recoverProduct(int p) {
		if (productRepository.containsKey(p)) {
			return productRepository.get(p);
		} else {
			throw new NoSuchElementException();
		}
	}
	
	void updateProduct(Produto p) {
		
	}
	
	void deleteProduct(Produto p) {
		productRepository.remove(p.getId());
	}
}
