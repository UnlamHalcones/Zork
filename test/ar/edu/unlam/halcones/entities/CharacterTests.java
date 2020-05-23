package ar.edu.unlam.halcones.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CharacterTests {

	Character character;
		
	@Test
	public void queSeMueveAOtroLocationSinObstaculo() {
		Location locationDestino = new Location("Location Destino", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		Trigger dummyTrigger = new Trigger("item", "rociador", "on trigger", "after trigger");

		Npc dummyNpc = new Npc("I'm a dummy npc", "I'm a dummy npc", Arrays.asList(dummyTrigger));

		Connection aConnection = new Connection("south", locationDestino, null); // A la connection no le seteo el obstaculo

		Location locationInicial = new Location("Location sin obstaculo", Collections.emptyList(), Arrays.asList(dummyNpc),
				Arrays.asList(aConnection));
		character = new Character(locationInicial);
		
		try {
			character.moveTo(locationDestino);
		} catch (Exception e) {
			Assert.fail();
		}
		Assert.assertTrue(character.isInLocation(locationDestino));
		
	}
	
	@Test
	public void queNoSeMueveAOtroLocationConObstaculo()  {
		Location locationDestino = new Location("Location Destino", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		Trigger dummyTrigger = new Trigger("item", "rociador", "on trigger", "after trigger");

		Npc dummyNpc = new Npc("I'm a dummy npc", "I'm a dummy npc", Arrays.asList(dummyTrigger));

		Connection aConnection = new Connection("south", locationDestino, dummyNpc); // A la connection no le seteo el obstaculo

		Location locationInicial = new Location("Location sin obstaculo", Collections.emptyList(), Arrays.asList(dummyNpc),
				Arrays.asList(aConnection));
		character = new Character(locationInicial);
		
		character.moveTo(locationDestino);
		
		Assert.assertTrue(character.isInLocation(locationInicial));
		Assert.assertFalse(character.isInLocation(locationDestino));
	}
	
	@Test
	public void queNoSeMueveAOtroLocationSinConnection() {
		Location locationDestino = new Location("Location Destino", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		Trigger dummyTrigger = new Trigger("item", "rociador", "on trigger", "after trigger");

		Npc dummyNpc = new Npc("I'm a dummy npc", "I'm a dummy npc", Arrays.asList(dummyTrigger));

		Location locationInicial = new Location("Location sin obstaculo", Collections.emptyList(), Arrays.asList(dummyNpc),
				Collections.emptyList());
		character = new Character(locationInicial);
		
		character.moveTo(locationDestino);
		
		Assert.assertTrue(character.isInLocation(locationInicial));
		Assert.assertFalse(character.isInLocation(locationDestino));
	}
	
	@Test
	public void queAgarraUnItem() {
		Item item1 = new Item("item1", "gender1", "number1");
		List<Item> itemListPlace1 = Arrays.asList(item1);
		Place place1 = new Place(itemListPlace1);
		
		Item item2 = new Item("item2", "gender2", "number2");
		List<Item> itemListPlace2 = Arrays.asList(item2);
		Place place2 = new Place(itemListPlace2);
		
		Location location = new Location("DESCRIPTION LOCATION", Arrays.asList(place1, place2), Collections.emptyList(),
				Collections.emptyList());
		
		character = new Character(location);
		try {
			character.agarrarItem(item1);
		} catch (Exception e) {
			Assert.fail();
		}
		Assert.assertTrue(character.isItemInInventory(item1));
		Assert.assertFalse(character.isItemInInventory(item2));
	}
	
	public void queAgarraUnItemDeUnPlace() {
		Item item1 = new Item("item1", "gender1", "number1");
		List<Item> itemListPlace1 = Arrays.asList(item1);
		Place place1 = new Place(itemListPlace1);
		
		Item item2 = new Item("item2", "gender2", "number2");
		List<Item> itemListPlace2 = Arrays.asList(item2);
		Place place2 = new Place(itemListPlace2);
		
		Location location = new Location("DESCRIPTION LOCATION", Arrays.asList(place1, place2), Collections.emptyList(),
				Collections.emptyList());
		
		character = new Character(location);
		try {
			character.agarrarItem(item1, place1);
		} catch (Exception e) {
			Assert.fail();
		}
		Assert.assertTrue(character.isItemInInventory(item1));
		Assert.assertFalse(character.isItemInInventory(item2));
	}
	
	@Test(expected = Exception.class)
	public void queNoAgarraUnItemInexistenteEnLocation() throws Exception {
		Item item1 = new Item("item1", "gender1", "number1");
		List<Item> itemListPlace1 = Arrays.asList(item1);
		Place place1 = new Place(itemListPlace1);
		
		Item item2 = new Item("item2", "gender2", "number2");
		List<Item> itemListPlace2 = Arrays.asList(item2);
		Place place2 = new Place(itemListPlace2);
		
		Location location = new Location("DESCRIPTION LOCATION", Arrays.asList(place1, place2), Collections.emptyList(),
				Collections.emptyList());
		
		character = new Character(location);
		
		character.agarrarItem(new Item("item3", "gender3", "number3"));
		
	}
	
	@Test(expected = Exception.class)
	public void queNoAgarraUnItemInexistenceEnUnPlace() throws Exception {
		Item item1 = new Item("item1", "gender1", "number1");
		List<Item> itemListPlace1 = Arrays.asList(item1);
		Place place1 = new Place(itemListPlace1);
		
		Item item2 = new Item("item2", "gender2", "number2");
		List<Item> itemListPlace2 = Arrays.asList(item2);
		Place place2 = new Place(itemListPlace2);
		
		Location location = new Location("DESCRIPTION LOCATION", Arrays.asList(place1, place2), Collections.emptyList(),
				Collections.emptyList());
		
		character = new Character(location);
		character.agarrarItem(item1, place2);;
	}
	
	public void usarItemQueEstaEnInventario() {
		
	}
	
	public void usarItemQueNoEstaEnInventario() {
		
	}
		
	
}
