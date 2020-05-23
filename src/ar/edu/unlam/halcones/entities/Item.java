package ar.edu.unlam.halcones.entities;

import java.util.List;

public class Item extends GameEntity implements Comparable<Item> {
	private List<String> actions;
	private List<String> effectsOver;

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Item(String name, String state) {
		super(name, state);
		// TODO Auto-generated constructor stub
	}

	public Item(String name, String gender, String number) {
		super(name, gender, number);
		// TODO Auto-generated constructor stub
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
