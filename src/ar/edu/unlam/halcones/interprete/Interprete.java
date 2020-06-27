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
import ar.edu.unlam.halcones.entities.Item;
import ar.edu.unlam.halcones.entities.Location;
import ar.edu.unlam.halcones.entities.Npc;

public class Interprete {
	
	private static Game game;	
	
	private final static String INVALIDCOMMAND = "No entendi lo que ingresaste. Intenta de nuevo por favor.";

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
		
		gameSelecting = false;
		
		while (gameSelecting) {

			input = in.next();

			if (!availableGames.contains(input.toLowerCase())) {
				imprimirSalida("No tengo ese juego master, elegite otro");
			} else {
				gameSelecting = false;
			}
		}

		selectedGame = input;
		selectedGame = "piratasfantasmas";

		// Logica para cargar el game
		
		GeneradorDeGame generador = new GeneradorDeGame();
		
		
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
		
		for (Map.Entry<String, INombrable> entry : game.interactuables.entrySet()) {
			System.out.println(entry.getKey());
		}

		while (keepPlaying) {

			System.out.print("Enter something:");
			input = in.nextLine();

			if (input == "stop")
				keepPlaying = false;
			/// abrir puerta

			// Investigando, encontr� que no hay verbos con mas de una palabra en espa�ol (
			// o si lo hay, no son los comunes).
			// Entonces vamos a asumir que la primera palabra va a ser el verbo
			System.out.println("input:" + input);
			if (!input.contains(" ")) {
				imprimirSalida(INVALIDCOMMAND);
				continue;
			}

			verbo = input.substring(0, input.indexOf(" "));
			input = input.replace(verbo, "");

			if (!verbos.containsKey(verbo)) {
				imprimirSalida(INVALIDCOMMAND);
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
				imprimirSalida(INVALIDCOMMAND);
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

			salida = commandRouter(verbo, primerSustantivo, segundoSustantivo);
			
			imprimirSalida(salida);

		}
	}

	public static void imprimirSalida(String mensaje) {
		System.out.println(mensaje);
	}
	
	private static String commandRouter (String verbo, String primerSustantivo, String segundoSustantivo) {
		
		INombrable entidadUno = null;
		INombrable entidadDos = null;
		
		if(primerSustantivo!= "")
			entidadUno = game.interactuables.get(primerSustantivo);	
		
		if (segundoSustantivo != "")
			entidadDos = game.interactuables.get(segundoSustantivo);
		
		if (verbo.equals("ver"))
		{
			if(entidadUno != null)
			{
				if(entidadUno instanceof Item)
				{
					Item item = (Item) entidadUno.getEntity();
					
					//Si no tiene el item en el inventario digo que es un comando invalido para no dar informaci�n de que ese item existe
					
					if(!game.getCharacter().isItemInInventory(item))
						return INVALIDCOMMAND;
				}
				
				return entidadUno.ver();
			}
		}
		
		if (verbo.equals("usar"))
		{
			if (!(entidadUno instanceof Item) && !(entidadDos instanceof Item))
				return INVALIDCOMMAND;
		}
		
		return "";
	}
	

}
