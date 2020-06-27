package ar.edu.unlam.halcones.entities;

import java.util.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javafx.util.Pair;

public class GameTests {
	
	private Game game;
	private Character character;
	private List<Location> locations;
	private List<Npc> npcs;
	private List<Item> items;
	private List<EndGame> endGames;
	private Inventory inventory;

	@Before	
	public void initialize () {


		npcs = new ArrayList<Npc>();
		npcs.add(new Npc("Pirata Fantasma","boring"));
		npcs.add(new Npc("dragon","alive")); // nacio muerto por cuestiones practicas

		locations = new ArrayList<Location>();
		locations.add(new Location("taberna"));
		locations.add(new Location("muelle"));

		items = new ArrayList<Item>();
		items.add(new Item("espejo","unWatch"));
		
		endGames = new ArrayList<EndGame>();
		endGames.add(new EndGame("location","move","taberna",
				"¡Enhorabuena! Llegaste a la taberna, donde te espera una noche de borrachera con Grog y "
						+ "otros colegas piratas."));
		
		endGames.add(new EndGame("inventory-item","mirar","espejo","¡Oh, no! Acabas de descubrir que tú también "
							+ "eres un pirata fantasma... ¡el horror!"));
		
		endGames.add(new EndGame("npc","state-death","dragon","¡Mataste al Dragonn!"));

		inventory = new Inventory();
		inventory.add(items);
	}
	
	@Test
	public void endGame_MoveTaberna() {
		character = new Character(locations.get(0), inventory, "TestGuy");
		game = new Game("Welcome", character ,locations,npcs,items,endGames);

		Pair<Boolean, String> result = game.checkEndgame("move", "taberna");
		
		String text = "¡Enhorabuena! Llegaste a la taberna, donde te espera una noche de borrachera con Grog y otros colegas piratas.";
		
		Assert.assertTrue(result.getKey());
        Assert.assertEquals(text,result.getValue());
	}

	@Test
	public void endGame_LookMirror() {
		character = new Character(locations.get(1), inventory, "TestGuy");
		game = new Game("Welcome", character ,locations,npcs,items,endGames);

		Pair<Boolean, String> result;
		
		result = game.checkEndgame("mirar", "espejo");
		
		String text = "¡Oh, no! Acabas de descubrir que tú también "
				+ "eres un pirata fantasma... ¡el horror!";
		
		Assert.assertTrue(result.getKey());
		Assert.assertEquals(text,result.getValue());
	}
}
