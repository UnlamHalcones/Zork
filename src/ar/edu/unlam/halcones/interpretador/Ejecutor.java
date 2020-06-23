package ar.edu.unlam.halcones.interpretador;

import ar.edu.unlam.halcones.*;
import ar.edu.unlam.halcones.entities.*;
import ar.edu.unlam.halcones.entities.Character;

public class Ejecutor {

	public static void run(GameEntity entity1, String accion) {
		
		try {
		
		switch (entity1.type) {
		
		case ITEM:
			Item item = (Item) entity1;
			item.triggerThis(accion);
			break;
			
		case NPC:
			Npc npc = (Npc) entity1;
			npc.triggerThis(accion);
			break;
			
		case CHARACTER:
			Character character = (Character) entity1;
			character.triggerThis(accion);
			break;
			
		default:
			break;
		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void run(String entity1, String entity2, String accion) {

	}
}
