package ar.edu.unlam.halcones.entities;

import com.sun.xml.internal.ws.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	private List<Item> items;

	public Inventory(List<Item> items) {
		super();
		this.items = items;
	}

	public Inventory() {
		super();
		this.items = new ArrayList<Item>();
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void add(Item item) {
		if (!this.items.contains(item)) {
			System.out.println("Agregaste " + item.getFullDescription() + " al inventario.");
			this.items.add(item);
		}
	}

	public void remove(Item item) {
		if (this.items.contains(item)) {
			System.out.println(StringUtils.capitalize(item.getFullDescription()) + " ya no está en tu inventario.");
			this.items.remove(item);
		}
	}

	public void showItems() {
		if (this.items.size() > 0) {
			System.out.println("Tienes los siguientes items en el inventario:");
			for (Item item : getItems()) {
				System.out.println("- " + item.getName());
			}
		} else {
			System.out.println("No tienes items en tu inventario.");
		}
	}

	public boolean hasItem(Item item) {
		return items.contains(item);
	}
}
