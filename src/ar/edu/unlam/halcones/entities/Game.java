package ar.edu.unlam.halcones.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;

public class Game {

	private List<Location> locations;
	private List<Npc> npcs;
	private List<Item> items;
	private List<EndGame> endGames;
	private String welcome;
	private Character character;
	private List<GameEntity> gameEntities;
	public Map<String, INombrable> interactuables = new HashMap<String, INombrable>();
	
	
	public Game(String welcome, Character character, List<Location> locations, List<Npc> npcs, List<Item> items,
			List<EndGame> endGames) {
		this.locations = locations;
		this.npcs = npcs;
		this.items = items;
		this.endGames = endGames;
		this.welcome = welcome;
		this.character = character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}
	
	public String getWelcome() {
		return this.welcome;
	}

	public Character getCharacter() {
		return this.character;
	}


	public Pair<Boolean, String> checkEndgame(ActionDTO actionPerformed) {

		// Se iteran todos los Endgame verificando si se cumplen sus condiciones

		for (EndGame endGame_IT : endGames) {
			if (endGame_IT.getCondition().equals("location")) {

				if (character.isInLocation(endGame_IT.getThing()))
					return new Pair<Boolean, String>(true, endGame_IT.getDescription());

			} else if (endGame_IT.getCondition().equals("action")) {

				if ( actionPerformed.isPerformed() && endGame_IT.getThing().equals(actionPerformed.getThing())  && endGame_IT.getAction().equals(actionPerformed.getCommand()))
					return new Pair<Boolean, String>(true, endGame_IT.getDescription());
			} else if (endGame_IT.getCondition().equals("vida")) {
				if(character.getVida() <= 0L) {
					return new Pair<Boolean, String>(true, endGame_IT.getDescription());
				}
			}

		}

		return new Pair<Boolean, String>(false, "");

	}

	public GameEntity findEntity(String gameEntityName) {

		for (GameEntity gameEntity_IT : this.gameEntities) {

			if (gameEntity_IT.getName().contentEquals(gameEntityName)) {
				return gameEntity_IT;
			}

		}
		return null;

	}

	public void generarInteractuables() {

		for (Item item : items) {
			this.interactuables.putAll(item.getNombres());
		}

		for (Npc npc : npcs) {
			this.interactuables.putAll(npc.getNombres());
		}

		for (Location location : locations) {
			this.interactuables.putAll(location.getNombres());

			for (Place place : location.getPlaces()) {
				this.interactuables.putAll(place.getNombres());
			}

			for (Connection connection : location.getConnections()) {
				this.interactuables.putAll(connection.getNombres());
			}
		}

		// AGREGO PUNTOS CARDINALES
		this.interactuables.put("norte", new Location("NORTE"));
		this.interactuables.put("sur", new Location("SUR"));
		this.interactuables.put("este", new Location("ESTE"));
		this.interactuables.put("oeste", new Location("OESTE"));

		this.interactuables.putAll(character.getNombres());
		
		this.interactuables.putAll(character.getNombresLocation());

		this.interactuables.putAll(character.getInventory().getNombres());
	}
	
	public void actualizarInteractuables() {
		this.interactuables.putAll(character.getNombres());
		this.interactuables.putAll(character.getNombresLocation());
	}

	public void removeNpc(String npcName) {
		Npc npcABorrar = this.npcs.stream().filter(npc -> npc.getName().equals(npcName))
				.findAny()
				.orElse(null);
		if(npcABorrar != null) {
			this.npcs.remove(npcABorrar);

			// Ahora lo saco de cada location,  si esta en la connection lo pongo en null
			for(Location location : this.locations) {
				location.removerNpc(npcABorrar);
			}

			// Lo saco de los interactuables
			this.interactuables.remove(npcName);
		}
	}

	public void removeItemFromCharacter(String itemName) {
		Item item = this.items.stream().filter(item1 -> item1.getName().equals(itemName))
				.findFirst()
				.orElse(null);
		character.removerItemDeInventario(item);

	}

	public void modificarVidaCharacter(String substring) {
		this.character.modificarVida(Long.valueOf(substring));
	}

	public void crearNuevoItem(String itemName) {
		this.items.stream().filter(i -> i.getName().equals(itemName))
				.findAny()
				.ifPresent(i -> this.character.agregarItemAlInventario(i));
	}
}
