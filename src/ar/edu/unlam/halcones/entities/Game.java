package ar.edu.unlam.halcones.entities;

import java.util.List;

public class Game {
	private String welcome;
	private String character;
	private List<Location> locations;
	private List<Npc> npcs;
	private List<Item> items;
	private List<EndGame> endGame;

	public Game(String welcome, String character, List<Location> locations, List<Npc> npcs, List<Item> items,
			List<EndGame> endGame) {
		super();
		this.welcome = welcome;
		this.character = character;
		this.locations = locations;
		this.npcs = npcs;
		this.items = items;
		this.endGame = endGame;
	}

	public String getWelcome() {
		return welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public List<Npc> getNpcs() {
		return npcs;
	}

	public void setNpcs(List<Npc> npcs) {
		this.npcs = npcs;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<EndGame> getEndGame() {
		return endGame;
	}

	public void setEndGame(List<EndGame> endGame) {
		this.endGame = endGame;
	}

}
