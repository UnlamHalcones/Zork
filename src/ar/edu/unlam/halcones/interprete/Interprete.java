package ar.edu.unlam.halcones.interprete;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ar.edu.unlam.halcones.entities.Game;

public class Interprete {

	public static void main(String[] args) {

		System.out.println("Welcome to the interpreter");

		String command = "";
		Scanner in = new Scanner(System.in);

		Boolean gameSelecting = true;

		List<String> availableGames = new ArrayList<String>();

		availableGames.add("fantasmas");

		availableGames.add("autos");

		System.out.println("Que juego queres jugar?");
		String selectedGame = "";
		String input = "";
		while (gameSelecting) {

			input = in.next();

			if (!availableGames.contains(input.toLowerCase())) {
				System.out.println("No tengo ese juego master, elegite otro");
			}
			else {
				gameSelecting = false;
			}
		}
		
		selectedGame = input;

		// Logica para cargar el game
		
		Boolean keepPlaying = true;
		
		String verbo = "";
		String primerSustantivo = "" ;
		String segundoSustantivo = "" ;

		while (keepPlaying) {

			System.out.print("Enter something:");
			input = in.next();

			if (input == "stop")
				keepPlaying = false;
			
			
			/// abrir puerta
			
			//Investigando, encontré que no hay verbos con mas de una palabra en español ( o si lo hay, no son los comunes). 
			// Entonces vamos a asumir que la primera palabra va a ser el verbo
			
			

			System.out.println("You entered '" + input + "'");

		}

		System.out.print("Bye! :)");
	}
}
