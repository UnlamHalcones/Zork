package ar.edu.unlam.halcones.entities;

import java.util.List;
import java.util.Optional;

public class Item extends GameEntity implements Comparable<Item>, ITriggereable {
	private List<String> actions;
	private List<String> effectsOver;
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

	public String use(String action, ITriggereable over) {
		System.out.println("Action:" + action);
		System.out.println("actions:" + actions);

		if(!canDoAction(action)) {
			return "El item no puede realizar la accion";
		}
		
		if (!effectsOver.contains(over.getType())) {
			return "Accion no valida sobre un " + over.getType() + ".";
		}

		Trigger trigger = new Trigger("item", this.getName());

		return over.execute(trigger);
	}
	
	private boolean canDoAction(String action) {
		return this.actions.contains(action);
	}

	@Override
	public String getType() {
		return "item";
	}
	
	@Override
	public String execute(Trigger trigger) {
		Optional<Trigger> aux = triggers.stream()
				.filter(t -> t.getType().equals(trigger.getType()) && t.getThing().equals(trigger.getThing()))
				.findAny();

		if (!aux.isPresent()) {
			return "Accion no valida en el Item";
		}

		status = aux.get().getAfterTrigger();

		return aux.get().getOnTrigger();
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

	public void setEffectsOver(List<String> effectsOver) {
		this.effectsOver = effectsOver;
	}

	@Override
	public int compareTo(Item other) {
		String myName = this.getName();
		String otherName = other.getName();

		return myName.compareTo(otherName);
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

}
