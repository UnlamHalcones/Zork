package ar.edu.unlam.halcones.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.util.Pair;

public class Game {

	private String welcome;
	private String characterName;
	private List<Location> locations;
	private List<Npc> npcs;
	private List<Item> items;
	private List<EndGame> endGames;
	private Character character;
	
	private List<GameEntity> gameEntities;

	public Game(String welcome, String character, List<Location> locations, List<Npc> npcs, List<Item> items,
			List<EndGame> endGame) {
		super();
		this.welcome = welcome;
		this.characterName = character;
		this.locations = locations;
		this.npcs = npcs;
		this.items = items;
		this.endGames = endGame;
	}

	/*
	 * "endgames": [
    {
      "condition": "location",
      "action": "move",
      "thing": "taberna",
      "description": "¡Enhorabuena! Llegaste a la taberna, donde te espera una noche de borrachera con Grog y otros colegas piratas."
    },
    {
      "condition": "action",
      "action": "look",
      "thing": "espejo",
      "description": "i"
    }
    
    {
      "condition": "npc",
      "action": "state-death",
      "thing": "dragon",
      "description": "¡Mataste al Dragon!!!!"
    }
  ]
}
	 * 
	 */

	// veo el parametro condition para analizar cual GameEntity iterar
	// busco el objeto por nombre thing
	// if(objeto.getState.contentEquals(endGame_IT.getCondition))
	// es endGame, return String
	
	public Pair<Boolean, String> checkEndgame(String action, String thing) {

		// Se iteran todos los Endgame verificando si se cumplen sus condiciones

		for (EndGame endGame_IT : endGames) {

			// Como caso especial, si se trata de verificar un estado de un GameEntity, lo
			// tratamos en este if

			if (endGame_IT.getAction().contains("state")) {

				if (endGame_IT.getCondition().toLowerCase().contentEquals("npc")) { // si es un npc iteramos los npc
																					// disponibles

					// for (Npc npc_IT : npcs) {
					Optional<Npc> npcOptional = npcs.stream()
							.filter(n -> n.getName().contentEquals(endGame_IT.getThing())).findAny();

					if (npcOptional.isPresent()) {
						Npc npc_IT = npcOptional.get();
						if (npc_IT.getState().contentEquals(endGame_IT.getAction().substring(6))) {

							return new Pair<Boolean, String>(true, endGame_IT.getDescription());

						}
					}

					// }

				} else if (endGame_IT.getCondition().toLowerCase().contentEquals("item")) { // si es un item iteramos
																							// los item disponibles

					Optional<Item> itemOptional = items.stream()
							.filter(i -> i.getName().contentEquals(endGame_IT.getThing())).findAny();

					if (itemOptional.isPresent()) {
						Item item_IT = itemOptional.get();
						if (item_IT.getState().contentEquals(endGame_IT.getAction().substring(5))) {

							return new Pair<Boolean, String>(true, endGame_IT.getDescription());

						}
					}
				}

			} else if (action.contentEquals(endGame_IT.getAction()) && thing.contentEquals(endGame_IT.getThing())) {

				return new Pair<Boolean, String>(true, endGame_IT.getDescription());

			}

		}

		return new Pair<Boolean, String>(false, "");

	}
	
	public GameEntity findEntity (String gameEntityName) {
		
		for (GameEntity gameEntity_IT : this.gameEntities) {
			
			if(gameEntity_IT.getName().contentEquals(gameEntityName)) {
				return gameEntity_IT;
			}
			
		}
		return null;
		
	}

}
