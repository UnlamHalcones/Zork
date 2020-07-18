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

		ActionDTO useResponse = usableItem.use("usar", dummyNpc);

		assertEquals("changeDone", useResponse.getResponse());
	}

	@Test
	public void usarItemValidoEnNpcAfterTrigger() {
		usableItem.setEffectsOver(effectsOverNpcs);
		usableItem.setActions(useAction);

		ActionDTO useResponse = usableItem.use("usar", dummyNpc);

		assertEquals("changeDone", useResponse.getResponse());
	}

	@Test
	public void usarItemValidoEnChararcterOnTrigger() {
		usableItem.setEffectsOver(effectsOverCharacter);
		usableItem.setActions(useAction);

		ActionDTO useResponse = usableItem.use("usar", character);

		assertEquals("changeDone", useResponse.getResponse());

	}

	@Test
	public void usarItemValidoEnChararcterAfterTrigger() {
		usableItem.setEffectsOver(effectsOverCharacter);
		usableItem.setActions(useAction);

		ActionDTO useResponse = usableItem.use("usar", character);

		assertTrue(useResponse.isPerformed());

	}

	@Test
	public void usarItemValidoEnOtroItemOnTrigger() {
		usableItem.setEffectsOver(effectsOverItem);
		usableItem.setActions(useAction);

		ActionDTO useResponse = usableItem.use("usar", dummyItem);

		assertEquals("changeDone", useResponse.getResponse());
	}

	@Test
	public void usarItemValidoEnOtroItemAfterTrigger() {
		usableItem.setEffectsOver(effectsOverItem);
		usableItem.setActions(useAction);

		ActionDTO useResponse = usableItem.use("usar", dummyItem);

		assertTrue(useResponse.isPerformed());
	}

	@Test
	public void usarItemQueNoTieneEffectOverNpc() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		ActionDTO useResponse = usableItem.use("usar", dummyNpc);
		assertEquals("Accion no valida sobre un npc.", useResponse.getResponse());
	}

	@Test
	public void usarItemQueNoTieneEffectOverSelf() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);

		ActionDTO useResponse = usableItem.use("usar", character);
		assertEquals("Accion no valida sobre un self.", useResponse.getResponse());
	}

	@Test
	public void usarItemQueNoTieneEffectOverOtroItem() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		ActionDTO useResponse = usableItem.use("usar", dummyItem);
		assertEquals("Accion no valida sobre un item.", useResponse.getResponse());
	}

	@Test
	public void usarItemQueNoTieneActionValidoNpc() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		ActionDTO useResponse = usableItem.use("comer", dummyNpc);
		assertEquals("El item no puede realizar la accion", useResponse.getResponse());
	}

	@Test
	public void usarItemQueNoTieneActionValidoCharacter() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);

		ActionDTO useResponse = usableItem.use("comer", character);
		assertEquals("El item no puede realizar la accion", useResponse.getResponse());
	}

	@Test
	public void usarItemQueNoTieneActionValidoOtroItem() {
		usableItem.setEffectsOver(effectsOverEmpty);
		usableItem.setActions(useAction);
		ActionDTO useResponse = usableItem.use("comer", dummyItem);
		assertEquals("El item no puede realizar la accion", useResponse.getResponse());
	}
}