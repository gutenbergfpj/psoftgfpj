package PsoftDemo;

public class Produto {

	private String name;
	private String brand;
	private double price;
	private Integer id;
	
	Produto(String name, String brand, double price){
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.id = Integer.valueOf(name+brand);
	}
	
	public int getId() {
		//TODO
		return this.id;
	}
}
