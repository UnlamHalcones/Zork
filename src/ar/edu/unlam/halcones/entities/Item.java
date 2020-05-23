package ar.edu.unlam.halcones.entities;

import java.util.List;
import java.util.Optional;

public class Item extends GameEntity implements Comparable<Item>, ITriggereable  {
	private List<String> actions;
	private List<String> effectsOver;
	private List<Trigger> triggers;
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(String name, String gender, String number) {
		super(name, gender, number);
		// TODO Auto-generated constructor stub
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

	public void setEffectsOver(List<String> effectsOver) {
		this.effectsOver = effectsOver;
	}

	@Override
	public int compareTo(Item other) {
		String myName = this.getName();
		String otherName = other.getName();

		return myName.compareTo(otherName);
	}
		
	public String Use(String action, Npc over) throws Exception {
		System.out.println("Action:" + action);
		System.out.println("actions:" + actions);
		
		checkAction(action);
		
		if(!effectsOver.contains("npcs"))
		{			
			throw new Exception("Acción no válida sobre un NPC.");
		}
		
		Trigger trigger = new Trigger("item", this.getName());
		
		return over.Execute(trigger);
	}
	
	public String Use(String action, Character over) throws Exception {
		checkAction(action);
		
		if(!this.effectsOver.contains("self"))
		{			
			throw new Exception("Acción no válida sobre ti mismo.");
		}
		
		Trigger trigger = new Trigger("item", this.getName());
		
		return over.Execute(trigger);
	}
	
	
	public String Use(String action, Item over) throws Exception {
		checkAction(action);
		
		if(!this.effectsOver.contains("item"))
		{			
			throw new Exception("Acción no válida sobre un item.");
		}
		
		Trigger trigger = new Trigger("item", this.getName());
		
		return over.Execute(trigger);
	}
	
	public void checkAction(String action) throws Exception{
		if (!this.actions.contains(action))
		{
			throw new Exception("Acción no valida para el item.");
		}
	}
	
	
	@Override
	public String Execute(Trigger trigger) throws Exception {
		Optional<Trigger> aux = triggers.stream().filter(t -> t.getType().equals(trigger.getType()) && t.getThing().equals(trigger.getThing())).findAny();	
		
		if (!aux.isPresent())
		{
			throw new Exception("Accion no valida en el Item");
		}
		
		status = aux.get().getAfterTrigger();
		
		return aux.get().getOnTrigger();
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
