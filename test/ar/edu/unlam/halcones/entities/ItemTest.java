package ar.edu.unlam.halcones.entities;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {

	//verificar estado
	Trigger dummyTrigger = new Trigger("item", "validItem", "changeDone", "removed");
	
	Npc dummyNpc = new Npc("I'm a dummy npc", "I'm a dummy npc", Arrays.asList(dummyTrigger));
	Character character = new Character(Arrays.asList(dummyTrigger));
	
	Item dummyItem = new Item("dummy", "male", "singular", Arrays.asList(dummyTrigger));
	
	String result;
	Item usableItem;
	
	List<String> effectsOverEmpty =  Arrays.asList("empty");
	List<String> effectsOverNpcs =  Arrays.asList("npcs");
	List<String> effectsOverCharacter =  Arrays.asList("self");
	List<String> effectsOverItem =  Arrays.asList("item");
	
	List<String> useAction = Arrays.asList("usar");

	@Before 
	public void init() {
		usableItem = new Item("validItem", "", "");
	}
	
	
	@Test
	public void usarItemValidoEnNpcOnTrigger() {
		usableItem.setEffectsOver(effectsOverNpcs);
		usableItem.setActions(useAction);
		
		try {
			result = usableItem.Use("usar", dummyNpc);
		} catch (Exception e) {
			fail(e.toString());
		}
		
		assertEquals("changeDone", result);
	}
	
	@Test
	public void usarItemValidoEnNpcAfterTrigger() {
		usableItem.setEffectsOver(effectsOverNpcs);
		usableItem.setActions(useAction);
		
		try {
			result = usableItem.Use("usar", dummyNpc);
		} catch (Exception e) {
			fail(e.toString());
		}
		
		assertEquals("removed", dummyNpc.status);
	}
	
	
	@Test
	public void usarItemValidoEnChararcterOnTrigger() {
		usableItem.setEffectsOver(effectsOverCharacter);
		usableItem.setActions(useAction);
		
		try {
			result = usableItem.Use("usar", character);
		} catch (Exception e) {
			fail(e.toString());
		}
		
		assertEquals("changeDone", result);
		
	}
	
	@Test
	public void usarItemValidoEnChararcterAfterTrigger() {
		usableItem.setEffectsOver(effectsOverCharacter);
		usableItem.setActions(useAction);
		
		try {
			result = usableItem.Use("usar", character);
		} catch (Exception e) {
			fail(e.toString());
		}
		
		assertEquals("removed", character.status);
		
	}
	
	@Test
	public void usarItemValidoEnOtroItemOnTrigger() {
		usableItem.setEffectsOver(effectsOverItem);
		usableItem.setActions(useAction);
		
		try {
			result = usableItem.Use("usar", dummyItem);
		} catch (Exception e) {
			fail(e.toString());
		}
		
		assertEquals("changeDone", result);
	}
	
	@Test
	public void usarItemValidoEnOtroItemAfterTrigger() {
		usableItem.setEffectsOver(effectsOverItem);
		usableItem.setActions(useAction);
		
		try {
			result = usableItem.Use("usar", dummyItem);
		} catch (Exception e) {
			fail(e.toString());
		}
		
		assertEquals("removed", dummyItem.status);
	}
	

	@Test
	public void usarItemQueNoTieneEffectOverNpc() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		try {
			usableItem.Use("usar", dummyNpc);
		} catch (Exception e) {
			assertEquals("Accion no valida sobre un NPC.", e.getMessage());
		}
	}
	
	@Test
	public void usarItemQueNoTieneEffectOverSelf() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		
		try {
			usableItem.Use("usar", character);
		} catch (Exception e) {
			assertEquals("Accion no valida sobre ti mismo.", e.getMessage());
		}
	}
	
	@Test
	public void usarItemQueNoTieneEffectOverOtroItem() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		try {
			usableItem.Use("usar", dummyItem);
		} catch (Exception e) {
			assertEquals("Accion no valida sobre un item.", e.getMessage());
		}
	}
	
	
	@Test
	public void usarItemQueNoTieneActionValidoNpc() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		try {
			usableItem.Use("comer", dummyNpc);
		} catch (Exception e) {
			assertEquals("Accion no valida para el item.", e.getMessage());
		}
	}
	
	@Test
	public void usarItemQueNoTieneActionValidoCharacter() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		
		try {
			usableItem.Use("comer", character);
		} catch (Exception e) {
			assertEquals("Accion no valida para el item.", e.getMessage());
		}
	}
	
	@Test
	public void usarItemQueNoTieneActionValidoOtroItem() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		try {
			usableItem.Use("comer", dummyItem);
		} catch (Exception e) {
			assertEquals("Accion no valida para el item.", e.getMessage());
		}
	}
}
