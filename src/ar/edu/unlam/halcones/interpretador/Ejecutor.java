package ar.edu.unlam.halcones.interpretador;

import ar.edu.unlam.halcones.*;
import ar.edu.unlam.halcones.entities.*;

public class Ejecutor {

	public static void run(GameEntity entity1, String accion) {

		switch (entity1.type) {
		case ITEM:
			Item item = (Item) entity1;
			break;
		case NPC:
			Npc npc = (Npc) entity1;
			break;
		case LOCATION:

			break;
		case PLACE:


			break;

		default:
			break;
		}

	}

	public static void run(String entity1, String entity2, String accion) {

	}
}
