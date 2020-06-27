package ar.edu.unlam.halcones.entities;

import java.util.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javafx.util.Pair;

public class GameTests {
	
	private Game game;
	
	@Before	
	public void initialize () {


		List<Npc> npcs = new ArrayList<Npc>();
		npcs.add(new Npc("Pirata Fantasma","boring"));
		npcs.add(new Npc("dragon","alive")); // nacio muerto por cuestiones practicas

		List<Location> locations = new ArrayList<Location>();
		locations.add(new Location("taberna", new LinkedList(), npcs, new LinkedList<>()));
		locations.add(new Location("muelle"));

		List<Item> items = new ArrayList<Item>();
		items.add(new Item("espejo","unWatch"));
		
		List<EndGame> endGames = new ArrayList<EndGame>();
		endGames.add(new EndGame("location","move","taberna",
				"¡Enhorabuena! Llegaste a la taberna, donde te espera una noche de borrachera con Grog y "
						+ "otros colegas piratas."));
		
		endGames.add(new EndGame("action","look","espejo","¡Oh, no! Acabas de descubrir que tú también "
							+ "eres un pirata fantasma... ¡el horror!"));
		
		endGames.add(new EndGame("npc","state-death","dragon","¡Mataste al Dragonn!"));

		Character testGuy = new Character(locations.get(0), new Inventory(), "TestGuy");
		game = new Game("Welcome", testGuy ,locations,npcs,items,endGames);
	}
	
	
	
	@Test
	public void endGame_ThereIsNotEndGametest() {
		
		Assert.assertFalse(game.checkEndgame("move", "muelle").getKey());
		
	}
	
	@Test
	public void endGame_MoveTaberna() {
		
		Pair<Boolean, String> result = game.checkEndgame("move", "taberna");
		
		String text = "¡Enhorabuena! Llegaste a la taberna, donde te espera una noche de borrachera con Grog y otros colegas piratas.";
		
		Assert.assertTrue(result.getKey());
        Assert.assertEquals(text,result.getValue());
	}

	@Test
	public void endGame_LookMirror() {
		
		
		Pair<Boolean, String> result;
		
		result =game.checkEndgame("look", "espejo");
		
		String text = "¡Oh, no! Acabas de descubrir que tú también "
				+ "eres un pirata fantasma... ¡el horror!";
		
		Assert.assertTrue(result.getKey());
		Assert.assertEquals(text,result.getValue());
	}
}
