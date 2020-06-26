package ar.edu.unlam.halcones.interprete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ar.edu.unlam.halcones.archivo.GeneradorDeGame;
import ar.edu.unlam.halcones.entities.Connection;
import ar.edu.unlam.halcones.entities.Game;
import ar.edu.unlam.halcones.entities.GameEntity;
import ar.edu.unlam.halcones.entities.INombrable;
import ar.edu.unlam.halcones.entities.ITriggereable;
import ar.edu.unlam.halcones.entities.Inventory;
import ar.edu.unlam.halcones.entities.Location;

public class Interprete {

	public static void main(String[] args) {

		System.out.println("Welcome to the interpreter");

		String command = "";
		Scanner in = new Scanner(System.in);

		Boolean gameSelecting = true;

		List<String> availableGames = new ArrayList<String>();

		Map<String, String> verbos = new HashMap<String, String>();

		verbos.put("usar", "usar");
		verbos.put("utilizar", "usar");

		verbos.put("utilizar", "atacar");
		verbos.put("golpear", "atacar");
		verbos.put("ir", "ir");
		verbos.put("avanzar", "ir");
		verbos.put("correr", "ir");
		verbos.put("ver", "ver");
		verbos.put("mirar", "ver");

		availableGames.add("piratasfantasmas");
		availableGames.add("pandemia");


		imprimirSalida("Que juego queres jugar?");
		String selectedGame = "";
		String input = "";
		while (gameSelecting) {

			input = in.next();

			if (!availableGames.contains(input.toLowerCase())) {
				imprimirSalida("No tengo ese juego master, elegite otro");
			} else {
				gameSelecting = false;
			}
		}

		selectedGame = input;

		// Logica para cargar el game
		
		GeneradorDeGame generador = new GeneradorDeGame();
		Game game = null;
		
		try {
			game = generador.generarEntornoDeJuego(selectedGame + ".json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Boolean keepPlaying = true;

		String verbo = "";
		String primerSustantivo = "";
		String segundoSustantivo = "";

		String invalidCommandMessage = "No entendi lo que ingresaste. Intenta de nuevo por favor.";

		while (keepPlaying) {

			System.out.print("Enter something:");
			input = in.nextLine();

			if (input == "stop")
				keepPlaying = false;
			/// abrir puerta

			// Investigando, encontré que no hay verbos con mas de una palabra en español (
			// o si lo hay, no son los comunes).
			// Entonces vamos a asumir que la primera palabra va a ser el verbo
			System.out.println("input:" + input);
			if (!input.contains(" ")) {
				imprimirSalida(invalidCommandMessage);
				continue;
			}

			verbo = input.substring(0, input.indexOf(" "));
			input = input.replace(verbo, "");

			System.out.println("Verbo:" + verbo);
			
			if (!verbos.containsKey(verbo)) {
				imprimirSalida(invalidCommandMessage);
				continue;
			}

			int indexPrimerEncontrado = 0;
			int indexSegundoEncontrado = 0;

			String primerEncontrado = "";
			String segundoEncontrado = "";

			for (Map.Entry<String, INombrable> entry : game.interactuables.entrySet()) {
				
				if (input.contains(entry.getKey())) {
					if (indexPrimerEncontrado == 0) {
						indexPrimerEncontrado = input.indexOf(entry.getKey());
						primerEncontrado = entry.getKey();
						input = input.replace(primerEncontrado, "");
					} else if (indexSegundoEncontrado == 0) {
						indexSegundoEncontrado = input.indexOf(entry.getKey());
						segundoEncontrado = entry.getKey();
						input = input.replace(segundoEncontrado, "");
					}
				}
			}

			if (indexPrimerEncontrado == 0) {
				imprimirSalida(invalidCommandMessage);
				continue;
			}
			
			if (indexPrimerEncontrado > 0 && indexSegundoEncontrado > 0)
			{
				if (indexPrimerEncontrado > indexSegundoEncontrado)
				{
					primerSustantivo = primerEncontrado;
					segundoSustantivo = segundoEncontrado;
				}
				else {
					primerSustantivo = segundoEncontrado;
					segundoSustantivo = primerEncontrado;
				}
			}
			else {
				primerSustantivo = primerEncontrado;
			}
			
			String salida = "El verbo es " + verbo + " - primer sustantivo:" + primerSustantivo + " - segundo sustantivo: " + segundoSustantivo;
			
			INombrable entidadUno = game.interactuables.get(primerSustantivo);	
			
			if (entidadUno instanceof Inventory)
			{
				if(verbo.equals("ver")) {
					imprimirSalida(game.getCharacter().getInventory().showItems());
				}
			}
			
			imprimirSalida(salida);

		}
	}

	public static void imprimirSalida(String mensaje) {
		System.out.println(mensaje);
	}
}
