package ar.edu.unlam.halcones.interprete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ar.edu.unlam.halcones.archivo.GeneradorDeGame;
import ar.edu.unlam.halcones.archivo.LectorDiccionarioCSV;
import ar.edu.unlam.halcones.entities.Connection;
import ar.edu.unlam.halcones.entities.Game;
import ar.edu.unlam.halcones.entities.GameEntity;
import ar.edu.unlam.halcones.entities.INombrable;
import ar.edu.unlam.halcones.entities.ITriggereable;
import ar.edu.unlam.halcones.entities.Inventory;
import ar.edu.unlam.halcones.entities.Item;
import ar.edu.unlam.halcones.entities.Location;
import ar.edu.unlam.halcones.entities.Npc;
import javafx.util.Pair;

public class Interprete {

	private static Game game;

	private final static String INVALIDCOMMAND = "No entendi lo que ingresaste. Intenta de nuevo por favor.";
	private final static String INVALIDCOMMANDONITEM = "No entendi lo que ingresaste. Intenta de nuevo por favor.";

	private static boolean keepPlaying = true;

	public static void main(String[] args) {

		System.out.println("Bienvenido al Zork de Zorks!");

		Scanner in = new Scanner(System.in);

		Boolean gameSelecting = true;

		List<String> availableGames = new ArrayList<String>();

		Map<String, String> verbos = new HashMap<String, String>();

		verbos = LectorDiccionarioCSV.leerDiccionario();

		availableGames.add("piratasfantasmas");
		availableGames.add("pandemia");
		
		imprimirSalida("Tengo los siguientes juegos disponibles:");
		for (String game: availableGames) {
			imprimirSalida(game);
		}

		imprimirSalida("\n");
		
		imprimirSalida("Que juego queres jugar?");
		String selectedGame = "";
		String input = "";

		while (gameSelecting) {

			input = in.nextLine();

			if (!availableGames.contains(input.toLowerCase())) {
				imprimirSalida("No tengo ese juego por favor elegí otro.\n");
			} else {
				gameSelecting = false;
			}
		}

		selectedGame = input;

		// Logica para cargar el game

		GeneradorDeGame generador = new GeneradorDeGame();

		try {
			game = generador.generarEntornoDeJuego(selectedGame + ".json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imprimirSalida(game.getWelcome());
		
		String verbo = "";
		String primerSustantivo = "";
		String segundoSustantivo = "";

		while (keepPlaying) {

			System.out.print("¿Que queres hacer?:\n");
			input = in.nextLine();

			if (input.equals("stop"))
				keepPlaying = false;
			/// abrir puerta

			// Investigando, encontré que no hay verbos con mas de una palabra en español (
			// o si lo hay, no son los comunes).
			// Entonces vamos a asumir que la primera palabra va a ser el verbo
			
			if (!input.contains(" ")) {
				imprimirSalida(INVALIDCOMMAND);
				continue;
			}

			input = input.toLowerCase();

			verbo = input.substring(0, input.indexOf(" "));
			input = input.replace(verbo, "");

			if (!verbos.containsKey(verbo)) {
				imprimirSalida(INVALIDCOMMAND);
				continue;
			}
			verbo = verbos.get(verbo);

			int indexPrimerEncontrado = 0;
			int indexSegundoEncontrado = 0;

			String primerEncontrado = "";
			String segundoEncontrado = "";
			primerSustantivo = "";
			segundoSustantivo = "";

			for (Map.Entry<String, INombrable> entry : game.interactuables.entrySet()) {
				if (input.contains(entry.getKey())) {
					if (indexPrimerEncontrado == 0) {
						indexPrimerEncontrado = input.indexOf(entry.getKey());
						primerEncontrado = entry.getKey();
					} else if (indexSegundoEncontrado == 0) {
						indexSegundoEncontrado = input.indexOf(entry.getKey());
						segundoEncontrado = entry.getKey();
					}
				}
			}

			if (indexPrimerEncontrado == 0) {
				imprimirSalida(INVALIDCOMMAND);
				continue;
			}

			if (indexPrimerEncontrado > 0 && indexSegundoEncontrado > 0) {
				if (indexSegundoEncontrado > indexPrimerEncontrado) {
					primerSustantivo = primerEncontrado;
					segundoSustantivo = segundoEncontrado;
				} else {
					primerSustantivo = segundoEncontrado;
					segundoSustantivo = primerEncontrado;
				}
			} else {
				primerSustantivo = primerEncontrado;
			}

			String salida = "El verbo es " + verbo + " - primer sustantivo:" + primerSustantivo
					+ " - segundo sustantivo: " + segundoSustantivo;

			salida = commandRouter(verbo, primerSustantivo, segundoSustantivo);

			imprimirSalida(salida);

		}

		imprimirSalida("Finalizaste el juego!");
	}

	public static void imprimirSalida(String mensaje) {
		System.out.println(mensaje);
	}

	private static String commandRouter(String verbo, String primerSustantivo, String segundoSustantivo) {

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
			keepPlaying = false;
			return checkEndgame.getValue();
		}

		return response;
	}

}
