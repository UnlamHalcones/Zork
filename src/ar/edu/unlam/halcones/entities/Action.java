package ar.edu.unlam.halcones.entities;

import java.util.ArrayList;
import java.util.List;

public class Action {

	private ArrayList<Item> objetos;
	private ArrayList<String> acciones;
	private String texto; // Es el que se muestra el ejecutarce la accion

	public Action(ArrayList<String> accion, ArrayList<Item> objetos, String texto) {

		this.objetos = objetos;
		this.acciones = accion;
		this.texto = texto;

	}
	
	public Action(String accion, Item objeto, String texto) {
		
		this.objetos.add(objeto);
		this.acciones.add(accion);
		this.texto=texto;
		
	}

	// -------------------- Ejecutar accion -----------------------------

	public void run(Item object) {
		
		boolean todoOk=false;
		
		if (this.acciones.get(0).contains("use"))
			todoOk=useAction((Item) object);

		if (this.acciones.get(0).contains("addInventory"))
			todoOk=addInventoryAction((Item) object);

		if (this.acciones.get(0).contains("changeState"))
			todoOk=changeStateAction(object,0);

		if(todoOk)
			System.out.println(this.texto); // lo puedo devolver asi lo verifica
		else
			System.out.println("No se pudo completar la accion");
	}

	public void run(ArrayList<Item> objects) {
		
		boolean todoOk=false;
		
		if (this.acciones.get(0).contains("use"))
			todoOk=useAction((Item) objects.get(0));

		if (this.acciones.get(0).contains("addInventory"))
			todoOk=addInventoryAction((Item) objects.get(0));

		if (this.acciones.get(0).contains("changeState"))
			todoOk=changeStateAction(objects.get(0),0);
		
		
		
		if (this.acciones.get(1).contains("use"))
			todoOk=useAction((Item) objects.get(1));

		if (this.acciones.get(1).contains("addInventory"))
			todoOk=addInventoryAction((Item) objects.get(1));

		if (this.acciones.get(1).contains("changeState"))
			todoOk=changeStateAction(objects.get(1),1);

		if(todoOk)
			System.out.println(this.texto); // lo puedo devolver asi lo verifica
		else
			System.out.println("No se pudo completar la accion");

	}

	public void run(Character player, Location location) {

		if (this.acciones.get(0).contains("movePlayer")) {
			
			movePlayerAction(player, location);
			System.out.println(this.texto);
			
		}else
			System.out.println("No se pudo completar la accion");
		
		

	}

	// -------------------- Acciones Predefinidas -----------------------------

	public static boolean useAction(Item item) {

		if (!item.esValido()) {
			System.out.println("Item no valido, no tiene usos");
			return false;
		}

		item.usarObjeto();
		return true;
	}

	public static boolean addInventoryAction(Item item) {

		if (!item.esValido()) {
			System.out.println("Item no valido, no tiene usos");
			return false;
		}

		if (item.getEnInventario())
			return false;
		else {
			item.setEnInventario(true);
			return true;
		}

	}

	public boolean changeStateAction(GameObject objeto, int pos) {

		int index = this.acciones.get(pos).indexOf(':');

		String newState = this.acciones.get(pos).substring(index+1); // ver si es +1 o no

		objeto.changeState(newState);
		
		return true;

	}

	public boolean movePlayerAction(Character player, Location location) {

		System.out.println("Codigo donde se mueve al jugador ...");
		return true;
	}

	// -------------------- Verifico si la accion es valida
	// -----------------------------

	public boolean accionValida(Item objeto) {

		int i = 0;
		boolean found = false;

		while (i < this.objetos.size() && !found) {

			if (this.objetos.get(i).equals(objeto))
				found = true;

		}

		return found;
	}

	public boolean accionValida(ArrayList<Item> objetos) {		 // mejorar con un solo while... Bastante fea

		int i = 0;
		int found = 0;

		while (i < this.objetos.size() && found != 0) {

			if (this.objetos.get(i).equals(objetos.get(0)))
				found++;

		}

		if (found == 1) {

			i = 0;

			while (i < this.objetos.size() && found!=2) {

				if (this.objetos.get(i).equals(objetos.get(1)))
					found++;

			}

		}
		
		if(found==2)
			return true;
		else
			return false;
	}
}
