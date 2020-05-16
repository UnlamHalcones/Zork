package ar.edu.unlam.halcones.entities.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unlam.halcones.entities.Inventory;
import ar.edu.unlam.halcones.entities.Item;

class InventoryTest {

	Inventory inventory;
	
	@BeforeEach  
	public void init() {
		inventory = new Inventory();
	}
	
	@Test
	void agregarUnItem() {
		Item dummyItem = new Item("dummy", "male", "singular");

		inventory.add(dummyItem);
		
		assertEquals(1, inventory.getItems().size());
	}
	
	@Test
	void agregarDosItems() {
		Item dummyItem = new Item("dummy", "male", "singular");
		Item dummyItem2 = new Item("dummy2", "female", "singular");

		inventory.add(dummyItem);
		inventory.add(dummyItem2);
		
		assertEquals(2, inventory.getItems().size());
	}
	
	@Test
	void agregarUnItemRepetido() {
		Item dummyItem = new Item("dummy", "male", "singular");
		

		inventory.add(dummyItem);
		
		assertEquals(1, inventory.getItems().size());
	}
	
	@Test
	void agregarYSacarUnItem() {
		Item dummyItem = new Item("dummy", "male", "singular");
		
		inventory.add(dummyItem);
		
		inventory.remove(dummyItem);
		
		assertEquals(0, inventory.getItems().size());
	}
	
	@Test
	void agregarDosItemsYSacarUno() {
		Item dummyItem = new Item("dummy", "male", "singular");
		Item dummyItem2 = new Item("dummy2", "female", "singular");

		inventory.add(dummyItem);
		inventory.add(dummyItem2);
		inventory.remove(dummyItem);
		assertEquals(1, inventory.getItems().size());
	}
}
