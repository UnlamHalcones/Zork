package ar.edu.unlam.halcones.game;

import ar.edu.unlam.halcones.entities.*;
import javafx.util.Pair;

public class Interprete {

	private final static String INVALIDCOMMAND = "No entendi lo que ingresaste. Intenta de nuevo por favor.";
	private final static String INVALIDCOMMANDONITEM = "No entendi lo que ingresaste. Intenta de nuevo por favor.";
	private boolean keepPlaying;
	
	public void setKeepPlaying(boolean keepPlaying) {
		this.keepPlaying = keepPlaying;
	}

	public Interprete() {
		this.keepPlaying = true;
	}
	
	public String commandRouter(Game game, String verbo, String primerSustantivo, String segundoSustantivo) {

		INombrable entidadUno = null;
		INombrable entidadDos = null;
		Boolean isTriggerAcction = true;
		ActionDTO responseAction = new ActionDTO(verbo, primerSustantivo);

		String response = INVALIDCOMMANDONITEM;

		if (!primerSustantivo.isEmpty())
			entidadUno = game.interactuables.get(primerSustantivo);

		if (!segundoSustantivo.isEmpty())
			entidadDos = game.interactuables.get(segundoSustantivo);

		if (verbo.equals("ver")) {
			isTriggerAcction = false;
			if (entidadUno != null) {
				if (entidadUno instanceof Item) {
					Item item = (Item) entidadUno.getEntity();

					// Si no tiene el item en el inventario digo que es un comando invalido para no
					// dar información de que ese item existe

					if (!game.getCharacter().isItemInInventory(item))
						return INVALIDCOMMAND;
				}
				if (entidadUno instanceof Location) {
					Location location = (Location) entidadUno;
					if(location.getName().equals("SUR") || location.getName().equals("NORTE") || location.getName().equals("ESTE") || location.getName().equals("OESTE")) {
						response = game.getCharacter().infoConexion(location);
					} else if (!game.getCharacter().isInLocation(location)) {
						return INVALIDCOMMAND;
					}
				}
				if (entidadUno instanceof Npc) {
					if (!(game.getCharacter().getLocation().isNpcInLocation((Npc)entidadUno))) {
						return INVALIDCOMMAND;
					}
					response = entidadUno.ver().getResponse();

				}
				if(response.equals(INVALIDCOMMANDONITEM)) {
					responseAction = entidadUno.ver();
					response = responseAction.getResponse();
				}
			}
		}


		if (verbo.equals("agarrar")) {
			isTriggerAcction = false;
			if (entidadUno != null && entidadUno instanceof Item) {
				Item item = (Item) entidadUno.getEntity();

				response = game.getCharacter().agarrarItem(item);
			}
		}

		if (verbo.equals("ir")) {
			isTriggerAcction = false;
			if (entidadUno != null && entidadUno instanceof Location) {
				Location location = (Location) entidadUno.getEntity();

				response = game.getCharacter().moveTo(location);
			}
		}

		if (verbo.equals("hablar")) {
			isTriggerAcction = false;
			if (entidadUno instanceof Npc) {
				if (!(game.getCharacter().getLocation().isNpcInLocation((Npc)entidadUno))) {
					return INVALIDCOMMAND;
				}
				Npc npc = (Npc) entidadUno.getEntity();

				response = npc.getTalk();
			}
		}

		if (isTriggerAcction) {
			if (!(entidadUno instanceof Item) && !(entidadDos instanceof Item))
				return INVALIDCOMMAND;

			Item item = null;
			ITriggereable triggerable = null;
			if (entidadUno instanceof Item) {
				item = (Item) entidadUno;

				if (entidadDos != null) {

					if (!(entidadDos instanceof ITriggereable))
						return INVALIDCOMMAND;

					triggerable = (ITriggereable) entidadDos;
				} else {
					triggerable = (ITriggereable) game.getCharacter();
				}

			} else {

				item = (Item) entidadDos;

				if (!(entidadUno instanceof ITriggereable))
					return INVALIDCOMMAND;


				triggerable = (ITriggereable) entidadUno;

			}

			if (!game.getCharacter().isItemInInventory(item))
				return "No tienes este item en tu inventario";

			if (triggerable instanceof Npc) {
				if (!(game.getCharacter().getLocation().isNpcInLocation((Npc) triggerable))) {
					return INVALIDCOMMAND;
				}
			}

			responseAction = item.use(verbo, triggerable);
			responseAction.setCommand(verbo);
			response = responseAction.getResponse();
		}

//		Pair<Boolean, String> checkEndgame = game.checkEndgame(verbo, primerSustantivo);
		Pair<Boolean, String> checkEndgame = game.checkEndgame(responseAction);

		if (checkEndgame.getKey().booleanValue()) {
			this.keepPlaying = false;
			return checkEndgame.getValue();
		}

		return response;
	}

	public boolean isKeepPlaying() {
		return this.keepPlaying;
	}
}
