package ar.edu.unlam.halcones.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CharacterTests {

	private Character character;
	private Location locationDestino;
	private Npc dummyNpc;
	private Npc pirataFantasmaNpc;
	private Item item1;
	private Place place1;
	private Item item2;
	private Place place2;

	@Before
	public void setUp() {
		locationDestino = new Location("Location Destino", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		Trigger dummyTrigger = new Trigger("item", "rociador", "on trigger", "after trigger");
		dummyNpc = new Npc("I'm a dummy npc", "I'm a dummy npc", Arrays.asList(dummyTrigger));

		item1 = new Item("item1", "gender1", "number1");
		item2 = new Item("item2", "gender2", "number2");

		List<Item> itemListPlace1 = new LinkedList<>(Arrays.asList(item1));
		place1 = new Place(itemListPlace1);

		List<Item> itemListPlace2 = new LinkedList<>(Arrays.asList(item2));
		place2 = new Place(itemListPlace2);

		pirataFantasmaNpc = new Npc("pirata fantasma", "male", "singular",
				"- '¡No puedes pasar!' El pirata fantasma no te dejará pasar",
				"¡No hay nada que me digas que me haga cambiar de opinión!", Collections.emptyList());
	}

	@Test
	public void queSeMueveAOtroLocationSinObstaculo() {
		Connection aConnection = new Connection("south", locationDestino, null); // A la connection no le seteo el obstaculo

		Location locationInicial = new Location("Location sin obstaculo", Collections.emptyList(),
				Arrays.asList(dummyNpc), Arrays.asList(aConnection));
		character = new Character(locationInicial);

		character.moveTo(locationDestino);
		Assert.assertTrue(character.isInLocation(locationDestino));

	}

	@Test
	public void queNoSeMueveAOtroLocationConObstaculo() {
		Connection aConnection = new Connection("south", locationDestino, dummyNpc); // A la connection le seteo el  obstaculo

		Location locationInicial = new Location("Location sin obstaculo", Collections.emptyList(),
				Arrays.asList(dummyNpc), Arrays.asList(aConnection));
		character = new Character(locationInicial);

		character.moveTo(locationDestino);

		Assert.assertTrue(character.isInLocation(locationInicial));
		Assert.assertFalse(character.isInLocation(locationDestino));
	}

	@Test
	public void queNoSeMueveAOtroLocationSinConnection() {
		Location locationInicial = new Location("Location sin obstaculo", Collections.emptyList(),
				Arrays.asList(dummyNpc), Collections.emptyList());
		character = new Character(locationInicial);

		character.moveTo(locationDestino);

		Assert.assertTrue(character.isInLocation(locationInicial));
		Assert.assertFalse(character.isInLocation(locationDestino));
	}

	@Test
	public void queAgarraUnItem() {
		Location location = new Location("DESCRIPTION LOCATION", Arrays.asList(place1, place2), new LinkedList<>(),
				new LinkedList<>());

		character = new Character(location);
		character.agarrarItem(item1);

		Assert.assertTrue(character.isItemInInventory(item1));
		Assert.assertFalse(character.isItemInInventory(item2));
	}

	@Test
	public void queAgarraUnItemDeUnPlace() {
		Location location = new Location("DESCRIPTION LOCATION", Arrays.asList(place1, place2), new LinkedList<>(),
				new LinkedList<>());

		character = new Character(location);
		character.agarrarItem(item1, place1);

		Assert.assertTrue(character.isItemInInventory(item1));
		Assert.assertFalse(character.isItemInInventory(item2));
	}

	@Test
	public void queNoAgarraUnItemInexistenteEnLocation() {
		Location location = new Location("DESCRIPTION LOCATION", Arrays.asList(place1, place2), Collections.emptyList(),
				Collections.emptyList());

		character = new Character(location);

        Item itemInexistente = new Item("item3", "gender3", "number3");
        character.agarrarItem(itemInexistente);
        Assert.assertFalse(character.isItemInInventory(itemInexistente));
	}

	@Test
	public void queNoAgarraUnItemInexistenceEnUnPlace() {
		Location location = new Location("DESCRIPTION LOCATION", Arrays.asList(place1, place2), Collections.emptyList(),
				Collections.emptyList());

		character = new Character(location);
		character.agarrarItem(item1, place2);

		Assert.assertFalse(character.isItemInInventory(item1));
	}

    @Test
	public void quePuedaInteractuarConNpc() {
		String talk = "";
		Location location = new Location("muelle", Collections.emptyList(), Arrays.asList(pirataFantasmaNpc),
				Collections.emptyList());

		character = new Character(location);

		talk = character.interactWithNpc(pirataFantasmaNpc);

		Assert.assertEquals("¡No hay nada que me digas que me haga cambiar de opinión!", talk);
	}

	@Test
	public void queNoPuedaInteractuarConNpc() {
		String talk = "";

		Location location = new Location("muelle", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		character = new Character(location);

		talk = character.interactWithNpc(pirataFantasmaNpc);

		Assert.assertEquals("No se encuentra el pirata fantasma en el lugar", talk);
	}

	// TODO estoy hay que verlo bien porque no hace bien la logica, de validar si se pudo usar el item
	@Test
	public void queUsaItemCuandoLoTiene() {
		Item dummyItem = new Item(Arrays.asList("usar"), Arrays.asList("self"));

		Inventory inventory = new Inventory();
		inventory.add(dummyItem);

		Location location = new Location("muelle", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		character = new Character(location, inventory);

		String result = character.usarItem(dummyItem, "usar", character);

		Assert.assertFalse(character.isItemInInventory(dummyItem));
		Assert.assertEquals("Eso no ha servido de nada", result);
	}

	// TODO estoy hay que verlo bien porque no hace bien la logica, de validar si se pudo usar el item
	@Test
	public void queNoUsaItemCuandoNoLoTiene() {
		Item dummyItem = new Item(Arrays.asList("usar"), Arrays.asList("remove"));

		Inventory inventory = new Inventory();
		inventory.add(dummyItem);

		Location location = new Location("muelle", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		character = new Character(location, inventory);
		character.usarItem(dummyItem, "usar", character);

		Assert.assertFalse(character.isItemInInventory(dummyItem));
	}
}
