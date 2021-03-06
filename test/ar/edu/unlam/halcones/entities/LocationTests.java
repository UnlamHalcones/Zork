package ar.edu.unlam.halcones.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class LocationTests {

	Location location;
	Location locationTaberna;

	@Test
	public void queNoSePuedeIrAOtraLocationPorObstaculo() {
		locationTaberna = new Location("Estas en una sucia taberna", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		Trigger trigger = new Trigger("item", "rociador", "on trigger", "after trigger");

		Npc npc = new Npc("No puedes pasar! El pirata fantasma no te dejara pasar",
				"No hay nada que me digas que me haga cambiar de opinion", Arrays.asList(trigger));

		Connection muelleConnection = new Connection("south", locationTaberna, npc);

		location = new Location("Estas en un muelle", Collections.emptyList(), Arrays.asList(npc),
				Arrays.asList(muelleConnection));

		String respuesta = location.goTo(locationTaberna);

		Assert.assertEquals("No puedes pasar! El pirata fantasma no te dejara pasar", respuesta);
	}

	@Test
	public void queNoSePuedeIrAOtraLocationPorFaltaDeConeccion() {
		locationTaberna = new Location("Estas en una sucia taberna", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		Trigger trigger = new Trigger("item", "rociador", "on trigger", "after trigger");

		Npc npc = new Npc("�No puedes pasar! El pirata fantasma no te dejara pasar",
				"�No hay nada que me digas que me haga cambiar de opinion", Arrays.asList(trigger));

		location = new Location("Estas en un muelle", Collections.emptyList(), Arrays.asList(npc),
				Collections.emptyList());

		String respuesta = location.goTo(locationTaberna);

		Assert.assertEquals("No se puede ir en esa direccion", respuesta);
	}

	@Test
	public void queSePuedeIrAOtraLocation() {
		locationTaberna = new Location("Estas en una sucia taberna", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		Trigger trigger = new Trigger("item", "rociador", "on trigger", "after trigger");

		Npc npc = new Npc("�No puedes pasar! El pirata fantasma no te dejara pasar",
				"�No hay nada que me digas que me haga cambiar de opinion", Arrays.asList(trigger));

		Connection muelleConnection = new Connection("south", locationTaberna, null); // A la connection no le seteo el
																						// obstaculo

		location = new Location("Estas en un muelle", Collections.emptyList(), Arrays.asList(npc),
				Arrays.asList(muelleConnection));

		String respuesta = location.goTo(locationTaberna);

		Assert.assertEquals("OK", respuesta);
	}

	@Test
	public void queDevuelveTrueSiExisteItemEnAlgunPlace() {
		Item item1 = new Item("item1", "gender1", "number1");
		List<Item> itemListPlace1 = Arrays.asList(item1);
		Place place1 = new Place(itemListPlace1);

		Item item2 = new Item("item2", "gender2", "number2");
		List<Item> itemListPlace2 = Arrays.asList(item2);
		Place place2 = new Place(itemListPlace2);

		location = new Location("DESCRIPTION LOCATION", Arrays.asList(place1, place2), Collections.emptyList(),
				Collections.emptyList());

		Assert.assertTrue(location.isItemInLocation(item1));
		Assert.assertTrue(location.isItemInLocation(item2));
	}

	@Test
	public void queDevuelveTrueSiExisteItemEnPlaceEspecifico() {
		Item item1 = new Item("item1", "gender1", "number1");
		List<Item> itemListPlace1 = Arrays.asList(item1);
		Place place1 = new Place(itemListPlace1);

		Item item2 = new Item("item2", "gender2", "number2");
		List<Item> itemListPlace2 = Arrays.asList(item2);
		Place place2 = new Place(itemListPlace2);

		location = new Location("DESCRIPTION LOCATION", Arrays.asList(place1, place2), Collections.emptyList(),
				Collections.emptyList());

		Assert.assertTrue(location.isItemInLocation(item1, place1));
		Assert.assertFalse(location.isItemInLocation(item2, place1));
	}

	@Test
	public void queDevuelveFalseSiNoExisteItemEnAlgunPlace() {
		Item item1 = new Item("item1", "gender1", "number1");
		List<Item> itemListPlace1 = Arrays.asList(item1);
		Place place1 = new Place(itemListPlace1);

		Item item2 = new Item("item2", "gender2", "number2");
		List<Item> itemListPlace2 = Arrays.asList(item2);
		Place place2 = new Place(itemListPlace2);

		location = new Location("DESCRIPTION LOCATION", Arrays.asList(place1, place2), Collections.emptyList(),
				Collections.emptyList());

		Assert.assertFalse(location.isItemInLocation(new Item("item3", "gender3", "number3")));
	}

	@Test
	public void queDevuelveFalseSiNoExisteItemEnPlaceEspecifico() {
		Item item1 = new Item("item1", "gender1", "number1");
		List<Item> itemListPlace1 = Arrays.asList(item1);
		Place place1 = new Place(itemListPlace1);

		Item item2 = new Item("item2", "gender2", "number2");
		List<Item> itemListPlace2 = Arrays.asList(item2);
		Place place2 = new Place(itemListPlace2);

		location = new Location("DESCRIPTION LOCATION", Arrays.asList(place1, place2), Collections.emptyList(),
				Collections.emptyList());

		Assert.assertFalse(location.isItemInLocation(item1, place2));
		Assert.assertFalse(location.isItemInLocation(item2, place1));
	}

	@Test
	public void queEncuentreNcpEnLocation() {
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '�No puedes pasar!' El pirata fantasma no te dejar� pasar",
				"�No hay nada que me digas que me haga cambiar de opini�n!", Collections.emptyList());

		location = new Location("Muelle", Collections.emptyList(), Arrays.asList(npc),
				Collections.emptyList());

		Assert.assertTrue(location.isNpcInLocation(npc));
	}
	
	@Test
	public void queNoEncuentreNcpEnLocation() {
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '�No puedes pasar!' El pirata fantasma no te dejar� pasar",
				"�No hay nada que me digas que me haga cambiar de opini�n!", Collections.emptyList());

		location = new Location("Muelle", Collections.emptyList(), Arrays.asList(npc),
				Collections.emptyList());

		Assert.assertFalse(location.isNpcInLocation(new Npc()));
	}
}
