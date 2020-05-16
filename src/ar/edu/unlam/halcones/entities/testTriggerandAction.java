package ar.edu.unlam.halcones.entities;

import org.junit.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.junit.Test;


public class testTriggerandAction {

	@Test
	public void testUsosDeUnItem() { //Comer Un caramelo
		
		System.out.println(""
				+ ""
				+ "************Test Comer un caramelo*********"
				+ ""
				+ "");
		
		
		Item caramelo = new Item();
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(caramelo);
		
		ArrayList<String> tipoAccion = new ArrayList<String>();
		tipoAccion.add("use");
		
		
		Action accionComerCaramelo = new Action(tipoAccion,items, "Te comiste el caramelo");
		
		boolean trigger1 = Trigger.actionAndObject(accionComerCaramelo, caramelo);
		Assert.assertTrue(trigger1);
		
		System.out.println("----------Ahora le agrego un uso al item-------------");
		
		caramelo.setUsos(1);
		
		boolean trigger2 = Trigger.actionAndObject(accionComerCaramelo, caramelo);
		
		Assert.assertTrue(trigger2);
		
		System.out.println("----------------Intento comerlo de vuelta-------------");
		
		boolean trigger3 = Trigger.actionAndObject(accionComerCaramelo, caramelo);
		
		Assert.assertTrue(trigger3);
	}
	
	@Test
	
	public void testUsarYCambiarDeEstado() {	//abrir una puerta con una llave
		
		System.out.println(""
				+ ""
				+ "************Test Abrir una puerta con una llave*********"
				+ ""
				+ "");
		
		Item llave = new Item();
		llave.setUsos(1);
		
		Item puerta = new Item();
		puerta.changeState("Cerrada");
		
		ArrayList<Item> items = new ArrayList<Item>();
		
		items.add(llave);
		items.add(puerta);
		
		ArrayList<String> tipoAccion = new ArrayList<String>();
		tipoAccion.add("use");
		tipoAccion.add("changeState:Abierta");
		
		Action abrirPuertaConLLave = new Action(tipoAccion, items,"Se abrio la puerta");
		
		System.out.println("El estado de la puerta antes de ejecutar el trigger es: "+puerta.getState());
		System.out.println("---------------Se ejecuta la accion abrir puerta con llave---------------");
		
		boolean trigger1 = Trigger.actionAndObject(abrirPuertaConLLave, items);
		Assert.assertTrue(trigger1);
		System.out.println("El estado de la puerta quedo en : " +puerta.getState());
		
	}
}
