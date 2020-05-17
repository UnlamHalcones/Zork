package ar.edu.unlam.halcones.entities.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ar.edu.unlam.halcones.entities.Connection;
import ar.edu.unlam.halcones.entities.GameEntity;
import ar.edu.unlam.halcones.entities.Item;
import ar.edu.unlam.halcones.entities.Location;
import ar.edu.unlam.halcones.entities.Npc;
import ar.edu.unlam.halcones.entities.Trigger;

public class NpcTest {

	@Test
	public void npcNoPuedeTriguearPorqueNoPoseeTriggers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '¡No puedes pasar!' El pirata fantasma no te dejará pasar",
				"¡No hay nada que me digas que me haga cambiar de opinión!", triggers);
		Assert.assertEquals(false, npc.canDoTrigger(new Item()));
	}

	@Test
	public void npcNoPuedeTriguearConUnItemQueNoExisteEnSuListaDeTriggers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(new Trigger("item", "rociador con cerveza de raiz",
				"- '¡Me encanta la cerveza de raiz!' El pirata fantasma se veía entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzó a desintegrarse. La mitad de arriba de su cuerpo se desvaneció, y las piernas inmediatamente echaron a correr.",
				"remove"));
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '¡No puedes pasar!' El pirata fantasma no te dejará pasar",
				"¡No hay nada que me digas que me haga cambiar de opinión!", triggers);
		Assert.assertEquals(false, npc.canDoTrigger(new Item()));
	}

	@Test
	public void npcNoPuedeTriguearConOtroNpcQueNoExisteEnSuListaDeTriggers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(new Trigger("item", "rociador con cerveza de raiz",
				"- '¡Me encanta la cerveza de raiz!' El pirata fantasma se veía entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzó a desintegrarse. La mitad de arriba de su cuerpo se desvaneció, y las piernas inmediatamente echaron a correr.",
				"remove"));
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '¡No puedes pasar!' El pirata fantasma no te dejará pasar",
				"¡No hay nada que me digas que me haga cambiar de opinión!", triggers);
		Assert.assertEquals(false, npc.canDoTrigger(new Npc()));
	}

	@Test
	public void npcPoseeItemsParaTriguearPeroNoElQueBuscoEnLaListaDeTriggers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(new Trigger("item", "rociador con cerveza de raiz",
				"- '¡Me encanta la cerveza de raiz!' El pirata fantasma se veía entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzó a desintegrarse. La mitad de arriba de su cuerpo se desvaneció, y las piernas inmediatamente echaron a correr.",
				"remove"));
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '¡No puedes pasar!' El pirata fantasma no te dejará pasar",
				"¡No hay nada que me digas que me haga cambiar de opinión!", triggers);
		Assert.assertEquals(false, npc.canDoTrigger(new Item("barreta", "female", "singular")));
	}

	@Test
	public void npcNoPuedeTriguearConAlgoQueNoHeredeDeGameEntity() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(new Trigger("connection", "", "", ""));
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '¡No puedes pasar!' El pirata fantasma no te dejará pasar",
				"¡No hay nada que me digas que me haga cambiar de opinión!", triggers);
		Assert.assertEquals(false, npc.canDoTrigger(new Connection("", new Location(), (GameEntity) npc)));
	}

	@Test
	public void npcPuedeTriguearPorqueElItemExisteEnSuListaDeTrigguers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(new Trigger("item", "rociador con cerveza de raiz",
				"- '¡Me encanta la cerveza de raiz!' El pirata fantasma se veía entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenzó a desintegrarse. La mitad de arriba de su cuerpo se desvaneció, y las piernas inmediatamente echaron a correr.",
				"remove"));
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '¡No puedes pasar!' El pirata fantasma no te dejará pasar",
				"¡No hay nada que me digas que me haga cambiar de opinión!", triggers);
		Assert.assertEquals(true, npc.canDoTrigger(new Item("rociador con cerveza de raiz", "male", "singular")));
	}

	@Test
	public void npcPuedeTriguearPorqueElOtroNpcExisteEnSuListaDeTrigguers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(new Trigger("npc", "pirata fantasma dos", "- '¡Vete de aquí!'.", "remove"));
		Npc npc = new Npc("pirata fantasma uno", "male", "singular",
				"- '¡No puedes pasar!' El pirata fantasma Uno no te dejará pasar", "", triggers);
		Assert.assertEquals(true, npc.canDoTrigger(new Npc("pirata fantasma dos", "male", "singular",
				"- '¡Dejadme pasar pirata fantasma uno!'", "", new ArrayList<Trigger>())));
	}

}
