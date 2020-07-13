package ar.edu.unlam.halcones.interprete;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

		Scanner in = new Scanner(System.in);

		Boolean isSelecting = true;
		
		String comandoIngresado = ""; 

		List<String> availableGames = new ArrayList<String>();

		Map<String, String> verbos = LectorDiccionarioCSV.leerDiccionario();

		imprimirSalida("Bienvenido al Zork de Zorks! ¿Cual es tu nombre?");
		String input = "";
		String playerName = "";
		
		while (isSelecting) {

			input = in.nextLine();

			if (input.isEmpty()) {
				imprimirSalida("Ingresa un nombre por favor.");
			} else {
				isSelecting = false;
				playerName = input;
			}
		}
		
		isSelecting = true;
		
		availableGames.add("piratasfantasmas");
		availableGames.add("pandemia");
		availableGames.add("pandemiaV1");

		imprimirSalida("Hola "+ playerName +"! Tengo los siguientes juegos disponibles:");
		for (String game: availableGames) {
			imprimirSalida(game);
		}
		
		imprimirSalida("Que juego queres jugar?");
		String selectedGame = "";
		

		while (isSelecting) {

			input = in.nextLine();

			if (!availableGames.contains(input.toLowerCase())) {
				imprimirSalida("No tengo ese juego por favor elegí otro.");
			} else {
				isSelecting = false;
			}
		}

		selectedGame = input;

		// Logica para cargar el game

		GeneradorDeGame generador = new GeneradorDeGame();
		
		String currentDate = new SimpleDateFormat("dd-MM-yyyy hh mm").format(Calendar.getInstance().getTime());
		String fileName = "Sesion de "+ playerName + " - " + selectedGame + " - " + currentDate;
		
		GuardadorHistoria guardador = new GuardadorHistoria(fileName);

		try {
			game = generador.generarEntornoDeJuego(selectedGame + ".json");
			game.generarInteractuables();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		imprimirSalida(game.getWelcome());
		
		guardador.agregarSalida(game.getWelcome());
		
		String verbo = "";
		String primerSustantivo = "";
		String segundoSustantivo = "";

		while (keepPlaying) {
			System.out.print("¿Que queres hacer?:");
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
			
			comandoIngresado = input;
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
			
			guardador.agregarEntrada(comandoIngresado);

			String salida = commandRouter(verbo, primerSustantivo, segundoSustantivo);
			
			guardador.agregarSalida(salida);
			
			imprimirSalida(salida);

		}

		imprimirSalida("Finalizaste el juego!");
		
		
		
		in.close();
	}

	public static void imprimirSalida(String mensaje) {
		System.out.println(mensaje);
	}

	private static String commandRouter(String verbo, String primerSustantivo, String segundoSustantivo) {

		INombrable entidadUno = null;
		INombrable entidadDos = null;
		Boolean isTriggerAcction = true;

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

				response = entidadUno.ver();
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

			response = item.use(verbo, triggerable);
		}

		Pair<Boolean, String> checkEndgame = game.checkEndgame(verbo, primerSustantivo);

		if (checkEndgame.getKey().booleanValue()) {
			keepPlaying = false;
			return checkEndgame.getValue();
		}

		return response;
	}

}
