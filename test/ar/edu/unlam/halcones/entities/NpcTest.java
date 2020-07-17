package ar.edu.unlam.halcones.entities;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class NpcTest {

	@Test
	public void npcNoPuedeTriguearPorqueNoPoseeTriggers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '�No puedes pasar!' El pirata fantasma no te dejar� pasar",
				"�No hay nada que me digas que me haga cambiar de opini�n!", triggers);
		Assert.assertEquals(false, npc.canDoTrigger(new Item()));
	}

	@Test
	public void npcNoPuedeTriguearConUnItemQueNoExisteEnSuListaDeTriggers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(new Trigger("item", "rociador con cerveza de raiz",
				"- '�Me encanta la cerveza de raiz!' El pirata fantasma se ve�a entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenz� a desintegrarse. La mitad de arriba de su cuerpo se desvaneci�, y las piernas inmediatamente echaron a correr.",
				"remove"));
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '�No puedes pasar!' El pirata fantasma no te dejar� pasar",
				"�No hay nada que me digas que me haga cambiar de opini�n!", triggers);
		Assert.assertEquals(false, npc.canDoTrigger(new Item()));
	}

	@Test
	public void npcNoPuedeTriguearConOtroNpcQueNoExisteEnSuListaDeTriggers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(new Trigger("item", "rociador con cerveza de raiz",
				"- '�Me encanta la cerveza de raiz!' El pirata fantasma se ve�a entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenz� a desintegrarse. La mitad de arriba de su cuerpo se desvaneci�, y las piernas inmediatamente echaron a correr.",
				"remove"));
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '�No puedes pasar!' El pirata fantasma no te dejar� pasar",
				"�No hay nada que me digas que me haga cambiar de opini�n!", triggers);
		Assert.assertEquals(false, npc.canDoTrigger(new Npc()));
	}

	@Test
	public void npcPoseeItemsParaTriguearPeroNoElQueBuscoEnLaListaDeTriggers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(new Trigger("item", "rociador con cerveza de raiz",
				"- '�Me encanta la cerveza de raiz!' El pirata fantasma se ve�a entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenz� a desintegrarse. La mitad de arriba de su cuerpo se desvaneci�, y las piernas inmediatamente echaron a correr.",
				"remove"));
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '�No puedes pasar!' El pirata fantasma no te dejar� pasar",
				"�No hay nada que me digas que me haga cambiar de opini�n!", triggers);
		Assert.assertEquals(false, npc.canDoTrigger(new Item("barreta", "female", "singular")));
	}

	@Test
	public void npcPuedeTriguearPorqueElItemExisteEnSuListaDeTrigguers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(new Trigger("item", "rociador con cerveza de raiz",
				"- '�Me encanta la cerveza de raiz!' El pirata fantasma se ve�a entusiasmado por tu ofrecimiento... sin embargo, cuando lo rociaste comenz� a desintegrarse. La mitad de arriba de su cuerpo se desvaneci�, y las piernas inmediatamente echaron a correr.",
				"remove"));
		Npc npc = new Npc("pirata fantasma", "male", "singular",
				"- '�No puedes pasar!' El pirata fantasma no te dejar� pasar",
				"�No hay nada que me digas que me haga cambiar de opini�n!", triggers);
		Assert.assertEquals(true, npc.canDoTrigger(new Item("rociador con cerveza de raiz", "male", "singular")));
	}

	@Test
	public void npcPuedeTriguearPorqueElOtroNpcExisteEnSuListaDeTrigguers() {
		List<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(new Trigger("npc", "pirata fantasma dos", "- '�Vete de aqu�!'.", "remove"));
		Npc npc = new Npc("pirata fantasma uno", "male", "singular",
				"- '�No puedes pasar!' El pirata fantasma Uno no te dejar� pasar", "", triggers);
		Assert.assertEquals(true, npc.canDoTrigger(new Npc("pirata fantasma dos", "male", "singular",
				"- '�Dejadme pasar pirata fantasma uno!'", "", new ArrayList<Trigger>())));
	}

}
