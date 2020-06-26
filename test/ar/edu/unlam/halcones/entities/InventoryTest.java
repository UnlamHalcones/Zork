package ar.edu.unlam.halcones.entities;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class InventoryTest {

	Inventory inventory;
	
	@Before
	public void init() {
		inventory = new Inventory();
	}
	
	@Test
	public void agregarUnItem() {
		Item dummyItem = new Item("dummy", "male", "singular");

		inventory.add(dummyItem);
		
		assertEquals(1, inventory.getItems().size());
	}
	
	@Test
	public void agregarDosItems() {
		Item dummyItem = new Item("dummy", "male", "singular");
		Item dummyItem2 = new Item("dummy2", "female", "singular");

		inventory.add(dummyItem);
		inventory.add(dummyItem2);
		
		assertEquals(2, inventory.getItems().size());
	}
	
	@Test
	public void agregarUnItemRepetido() {
		Item dummyItem = new Item("dummy", "male", "singular");
		

		inventory.add(dummyItem);
		
		assertEquals(1, inventory.getItems().size());
	}
	
	@Test
	public void sacarUnItem() {
		Item dummyItem = new Item("dummy", "male", "singular");
		inventory.add(dummyItem);
		inventory.remove(dummyItem);

		assertEquals(0, inventory.getItems().size());
	}
}
