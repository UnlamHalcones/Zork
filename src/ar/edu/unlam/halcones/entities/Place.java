package ar.edu.unlam.halcones.entities;

import java.util.List;

public class Place extends GameEntity {

	private List<Item> items;

	public Place() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Place(String name, String gender, String number) {
		super(name, gender, number);
		// TODO Auto-generated constructor stub
	}

	public Place(List<Item> items) {
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
