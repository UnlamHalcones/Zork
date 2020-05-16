package ar.edu.unlam.halcones.entities;

import java.util.List;

public class Item extends GameEntity  implements Comparable<Item>  {
	private List<String> actions;
	private List<String> effectsOver;
	public Item() {
		super();
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
}
