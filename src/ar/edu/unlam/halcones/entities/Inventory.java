package ar.edu.unlam.halcones.entities;

import java.util.List;

public class Inventory {
	private List<Item> items;

	public Inventory(List<Item> items) {
		super();
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
}
