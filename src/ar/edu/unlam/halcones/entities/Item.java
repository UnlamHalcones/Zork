package ar.edu.unlam.halcones.entities;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ar.edu.unlam.halcones.interprete.aftertriggers.Command;
import ar.edu.unlam.halcones.interprete.aftertriggers.HandlerAfterTrigger;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.*;

public class Item extends GameEntity implements Comparable<Item>, ITriggereable, INombrable<Item> {
	@JsonProperty("actions")
	private List<String> actions;

	@JsonProperty("effects_over")
	private List<String> effectsOver;

	@JsonProperty("triggers")
	private List<Trigger> triggers;

	public Item() {
		super();
	}

	public Item(String name, String state) {
		super(name, state);
	}

	public Item(String name, String gender, String number) {
		super(name, gender, number);
	}

	public Item(String name, String gender, String number, List<Trigger> triggers) {
		super(name, gender, number);
		this.triggers = triggers;
	}

	public Item(List<String> actions, List<String> effectsOver) {
		super();
		this.actions = actions;
		this.effectsOver = effectsOver;
	}

	public List<String> getActions() {
		return actions;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}

	public List<String> getEffectsOver() {
		return effectsOver;
	}
	
	public Icon getImage() {
		
		return new ImageIcon(getName()+".png");
	}

	public void setEffectsOver(List<String> effectsOver) {
		this.effectsOver = effectsOver;
	}

	@Override
	public int compareTo(Item other) {
		String myName = this.getName();
		String otherName = other.getName();

		return myName.compareTo(otherName);
	}

	public String use(String action, ITriggereable over) {

		if (!canDoAction(action)) {
			return "El item no puede realizar la accion";
		}

		if (!effectsOver.contains(over.getType())) {
			return "Accion no valida sobre un " + over.getType() + ".";
		}

		Trigger trigger = new Trigger("item", this.getName());

		// Esto comando siempre se ejecuta para poder sacar un item del inventario
		Command command = new Command("remove", this.getName(), this.getType());
		HandlerAfterTrigger.handleCommand(command);

		return over.execute(trigger);
	}

	private boolean canDoAction(String action) {
		return this.actions.contains(action);
	}

	@Override
	public String execute(Trigger trigger) {
		Trigger triggerToExecute = triggers.stream()
				.filter(t -> t.getType().equals(trigger.getType()) && t.getThing().equals(trigger.getThing())).findAny()
				.orElse(null);

		if (triggerToExecute == null) {
			return "Eso no ha servido de nada";
		}

		String afterTrigger = triggerToExecute.getAfterTrigger();
		if(afterTrigger != null) {
			String[] split = afterTrigger.split(",");
			for(String s : split) {
				Command command = new Command(s, this.getName(), this.getType());
				HandlerAfterTrigger.handleCommand(command);
			}
		}

		return triggerToExecute.getOnTrigger();
	}

	@Override
	public String getType() {
		return "item";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((actions == null) ? 0 : actions.hashCode());
		result = prime * result + ((effectsOver == null) ? 0 : effectsOver.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (actions == null) {
			if (other.actions != null)
				return false;
		} else if (!actions.equals(other.actions))
			return false;
		if (effectsOver == null) {
			if (other.effectsOver != null)
				return false;
		} else if (!effectsOver.equals(other.effectsOver))
			return false;
		return true;
	}

	@Override
	public void triggerThis(String action) {

		for (Trigger triggers_IT : triggers) {

			if (triggers_IT.getType().contentEquals(action)) {
				this.execute(triggers_IT);
				return;
			}

		}

	}

	public Map<String, Item> getNombres() {
		Map<String, Item> myMap = new HashMap<String, Item>();

		myMap.put(this.getName().trim(), this);
		myMap.put(this.getFullDescription().trim(), this);
		myMap.put(this.getFullDescriptionQty().trim(), this);

		return myMap;
	}

	@Override 
	public Item getEntity() {
		return this;
	}

	@Override
	public String ver() {
		return this.getFullDescription();
	}
}
