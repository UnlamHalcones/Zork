package ar.edu.unlam.halcones.game;

import ar.edu.unlam.halcones.entities.Game;
import ar.edu.unlam.halcones.entities.INombrable;
import ar.edu.unlam.halcones.entities.ITriggereable;
import ar.edu.unlam.halcones.entities.Item;
import ar.edu.unlam.halcones.entities.Location;
import ar.edu.unlam.halcones.entities.Npc;
import javafx.util.Pair;

public class Interprete_game {

	private final static String INVALIDCOMMAND = "No entendi lo que ingresaste. Intenta de nuevo por favor.";
	private final static String INVALIDCOMMANDONITEM = "No entendi lo que ingresaste. Intenta de nuevo por favor.";
	private boolean keepPlaying;
	
	public void setKeepPlaying(boolean keepPlaying) {
		this.keepPlaying = keepPlaying;
	}

	public Interprete_game() {
		keepPlaying = false;
	}
	
	public String commandRouter(Game game, String verbo, String primerSustantivo, String segundoSustantivo) {

		INombrable entidadUno = null;
		INombrable entidadDos = null;

		String response = INVALIDCOMMANDONITEM;

		if (!primerSustantivo.isEmpty())
			entidadUno = game.interactuables.get(primerSustantivo);

		if (!segundoSustantivo.isEmpty())
			entidadDos = game.interactuables.get(segundoSustantivo);

		if (verbo.equals("ver")) {
			if (entidadUno != null) {
				if (entidadUno instanceof Item) {
					Item item = (Item) entidadUno.getEntity();

					// Si no tiene el item en el inventario digo que es un comando invalido para no
					// dar información de que ese item existe

					if (!game.getCharacter().isItemInInventory(item))
						return INVALIDCOMMAND;
				}

				response = entidadUno.ver();
			}
		}

		if (verbo.equals("agarrar")) {
			if (entidadUno != null && entidadUno instanceof Item) {
				Item item = (Item) entidadUno.getEntity();

				response = game.getCharacter().agarrarItem(item);
			}
		}

		if (verbo.equals("ir")) {
			if (entidadUno != null && entidadUno instanceof Location) {
				Location location = (Location) entidadUno.getEntity();

				response = game.getCharacter().moveTo(location);
			}
		}

		if (verbo.equals("hablar")) {
			if (entidadUno instanceof Npc) {
				Npc npc = (Npc) entidadUno.getEntity();

				response = npc.getTalk();
			}
		}

		if (verbo.equals("usar")) {
			if (!(entidadUno instanceof Item) && !(entidadDos instanceof Item))
				return INVALIDCOMMAND;

			Item item = null;
			ITriggereable triggerable = null;
			if (entidadUno instanceof Item) {
				item = (Item) entidadUno;

				if (entidadDos != null) {
					triggerable = (ITriggereable) entidadDos;
				} else {
					triggerable = (ITriggereable) game.getCharacter();
				}

			} else {

				item = (Item) entidadDos;
				triggerable = (ITriggereable) entidadDos;

			}

			if (!game.getCharacter().isItemInInventory(item))
				return "No tienes este item en tu inventario";

			response = item.use("usar", triggerable);
		}

		Pair<Boolean, String> checkEndgame = game.checkEndgame(verbo, primerSustantivo);

		if (checkEndgame.getKey().booleanValue()) {
			this.keepPlaying = true;
			return checkEndgame.getValue();
		}

		return response;
	}

	public boolean isKeepPlaying() {
		return keepPlaying;
	}
}
