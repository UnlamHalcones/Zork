package ar.edu.unlam.halcones.entities;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {

	Item usableItem;
	Trigger dummyTrigger;
	Npc dummyNpc;
	Character character;
	Item dummyItem;

	List<String> effectsOverEmpty;
	List<String> effectsOverNpcs;
	List<String> effectsOverCharacter;
	List<String> effectsOverItem;
	List<String> useAction;

	@Before
	public void init() {
		dummyTrigger = new Trigger("item", "validItem", "changeDone", "removed");

		dummyNpc = new Npc("I'm a dummy npc", "I'm a dummy npc", Arrays.asList(dummyTrigger));
		character = new Character(Arrays.asList(dummyTrigger));

		dummyItem = new Item("dummy", "male", "singular", Arrays.asList(dummyTrigger));

		effectsOverEmpty = Arrays.asList("empty");
		effectsOverNpcs = Arrays.asList("npc");
		effectsOverCharacter = Arrays.asList("self");
		effectsOverItem = Arrays.asList("item");

		useAction = Arrays.asList("usar");

		usableItem = new Item("validItem", "", "");
	}

	@Test
	public void usarItemValidoEnNpcOnTrigger() {
		usableItem.setEffectsOver(effectsOverNpcs);
		usableItem.setActions(useAction);

		String result = usableItem.use("usar", dummyNpc);

		assertEquals("changeDone", result);
	}

	@Test
	public void usarItemValidoEnNpcAfterTrigger() {
		usableItem.setEffectsOver(effectsOverNpcs);
		usableItem.setActions(useAction);

		String result = usableItem.use("usar", dummyNpc);

		assertEquals("removed", dummyNpc.status);
	}

	@Test
	public void usarItemValidoEnChararcterOnTrigger() {
		usableItem.setEffectsOver(effectsOverCharacter);
		usableItem.setActions(useAction);

		String result = usableItem.use("usar", character);

		assertEquals("changeDone", result);

	}

	@Test
	public void usarItemValidoEnChararcterAfterTrigger() {
		usableItem.setEffectsOver(effectsOverCharacter);
		usableItem.setActions(useAction);

		String result = usableItem.use("usar", character);

		assertEquals("removed", character.status);

	}

	@Test
	public void usarItemValidoEnOtroItemOnTrigger() {
		usableItem.setEffectsOver(effectsOverItem);
		usableItem.setActions(useAction);

		String result = usableItem.use("usar", dummyItem);

		assertEquals("changeDone", result);
	}

	@Test
	public void usarItemValidoEnOtroItemAfterTrigger() {
		usableItem.setEffectsOver(effectsOverItem);
		usableItem.setActions(useAction);

		String result = usableItem.use("usar", dummyItem);

		assertEquals("removed", dummyItem.status);
	}

	@Test
	public void usarItemQueNoTieneEffectOverNpc() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		String result = usableItem.use("usar", dummyNpc);
		assertEquals("Accion no valida sobre un npc.", result);
	}

	@Test
	public void usarItemQueNoTieneEffectOverSelf() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);

		String result = usableItem.use("usar", character);
		assertEquals("Accion no valida sobre un self.", result);
	}

	@Test
	public void usarItemQueNoTieneEffectOverOtroItem() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		String result = usableItem.use("usar", dummyItem);
		assertEquals("Accion no valida sobre un item.", result);
	}

	@Test
	public void usarItemQueNoTieneActionValidoNpc() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		String result = usableItem.use("comer", dummyNpc);
		assertEquals("El item no puede realizar la accion", result);
	}

	@Test
	public void usarItemQueNoTieneActionValidoCharacter() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);

		String result = usableItem.use("comer", character);
		assertEquals("El item no puede realizar la accion", result);
	}

	@Test
	public void usarItemQueNoTieneActionValidoOtroItem() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		String result = usableItem.use("comer", dummyItem);
		assertEquals("El item no puede realizar la accion", result);
	}
}
