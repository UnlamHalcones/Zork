package ar.edu.unlam.halcones.interpretador;

import ar.edu.unlam.halcones.*;
import ar.edu.unlam.halcones.entities.*;
import ar.edu.unlam.halcones.entities.Character;

public class Ejecutor {

	public static void run(Character character, Item item, String accion) {

		switch (accion) {

		case "agarrar":
			character.agarrarItem(item);
			break;
			
		default:
			character.usarItem(item, accion, character);
		}

	}

	public static void run(Character character, Item item, ITriggereable entity2, String accion) {

		switch (accion) {

		case "":

			break;

		default:
			character.usarItem(item, accion, entity2);
			break;
		}

	}

	public static void run(Character character, Npc npc, String accion) {

		switch (accion) {
		case "interactuar":
			character.interactWithNpc(npc);
			break;

		default:
			break;
		}

	}

	public static void run(Character character) {

		character.mostrarInformacionDelInventario();

	}

	public static void run(Character character, Location location) {

		character.moveTo(location);

	}
}
