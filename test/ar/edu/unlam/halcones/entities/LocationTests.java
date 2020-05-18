package ar.edu.unlam.halcones.entities;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocationTests {

	Location location;
	Location locationTaberna;

	@Test(expected = Exception.class)
	public void queNoSePuedeIrAOtraLocationPorObstaculo() throws Exception {
		locationTaberna = new Location("Estas en una sucia taberna", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		Trigger trigger = new Trigger("item", "rociador", "on trigger", "after trigger");

		Npc npc = new Npc("¡No puedes pasar! El pirata fantasma no te dejara pasar",
				"¡No hay nada que me digas que me haga cambiar de opinion", Arrays.asList(trigger));

		Connection muelleConnection = new Connection("south", locationTaberna, npc);

		location = new Location("Estas en un muelle", Collections.emptyList(), Arrays.asList(npc),
				Arrays.asList(muelleConnection));
		try {
			location = location.goTo(locationTaberna);
		} catch (Exception e) {
			throw e;
		}
	}

	@Test(expected = Exception.class)
	public void queNoSePuedeIrAOtraLocationPorFaltaDeConeccion() throws Exception {
		locationTaberna = new Location("Estas en una sucia taberna", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		Trigger trigger = new Trigger("item", "rociador", "on trigger", "after trigger");

		Npc npc = new Npc("¡No puedes pasar! El pirata fantasma no te dejara pasar",
				"¡No hay nada que me digas que me haga cambiar de opinion", Arrays.asList(trigger));

		location = new Location("Estas en un muelle", Collections.emptyList(), Arrays.asList(npc),
				Collections.emptyList());
		try {
			location = location.goTo(locationTaberna);
		} catch (Exception e) {
			throw e;
		}
	}

	@Test
	public void queSePuedeIrAOtraLocation() {
		locationTaberna = new Location("Estas en una sucia taberna", Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList());

		Trigger trigger = new Trigger("item", "rociador", "on trigger", "after trigger");

		Npc npc = new Npc("¡No puedes pasar! El pirata fantasma no te dejara pasar",
				"¡No hay nada que me digas que me haga cambiar de opinion", Arrays.asList(trigger));

		Connection muelleConnection = new Connection("south", locationTaberna, null); // A la connection no le seteo el obstaculo

		location = new Location("Estas en un muelle", Collections.emptyList(), Arrays.asList(npc),
				Arrays.asList(muelleConnection));
		try {
			location = location.goTo(locationTaberna);
		} catch (Exception e) {
			Assert.fail();
		}
		
		Assert.assertEquals(locationTaberna, location);
	}
}
