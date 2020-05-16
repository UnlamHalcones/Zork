package ar.edu.unlam.halcones.entities;

import java.util.ArrayList;
import java.util.List;

public abstract class Trigger { // si el trigger tira false podemos poner, no se puede ejecutar esa accion o algo x el estilo

	public static boolean actionAndObject(Action accion, Item objeto) {

		if (!accion.accionValida(objeto))
			return false;

		accion.run(objeto);

		return true;

	}

	public static boolean actionAndObject(Action accion, ArrayList<Item> objetos) {
		
		if (accion.accionValida(objetos))
			return false;
		
		accion.run(objetos);
		
		return true;
	}

	public static void movePlayer(Action accion, Character player, Location location) {

		// validaciones si se puede mover, etc

		accion.run(player, location);

	}

}
