package ar.edu.unlam.halcones.entities;

import java.util.List;

public class Item extends GameEntity implements Comparable<Item>, ITriggereable  {
	private List<String> actions;
	private List<String> effectsOver;
	private int usesQty; 
	
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
		this.setUsesQty(1);
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
		
	//Antes tenes una lista de "interacciones" que podes tener
		
	//action item	(self) 
	
	//"dar" "espada" al caballero 
	public String ExecuteAction(String action, Npc over) {
		if (actions.contains(action))
			System.out.println("Acci�n no valida para el item");
		
		//Esto podr�a ser una exception
		if(effectsOver.contains("npcs"))
		{			
			return "Acci�n no v�lida sobre un NPC.";
		}
		
		Trigger trigger = new Trigger("item", this.getName(), "", "");
		
		String response = over.Execute(trigger);
		
		//-1 es usos ilimitados. Ej: una espada
		if (this.getUsesQty() != -1)
			this.setUsesQty(this.getUsesQty() - 1);
		
		return response;
	}
	
	public String ExecuteAction(String action, Character over) {
		//Esto podr�a ser una exception
		if (actions.contains(action))
		{
			return "Acci�n no valida para el item";
		}
		
		//Esto podr�a ser una exception
		if(effectsOver.contains("self"))
		{			
			return "Acci�n no v�lida sobre ti mismo";
		}
		
		Trigger trigger = new Trigger("item", this.getName(), "", "");
		
		String response = over.Execute(trigger);
		
		//-1 es usos ilimitados. Ej: una espada
		if (this.getUsesQty() != -1)
			this.setUsesQty(this.getUsesQty() - 1);
		
		return trigger.getOnTrigger();
	}
	
	
	public String ExecuteAction(String action, Item over) {
		//Esto podr�a ser una exception
		if (actions.contains(action))
		{
			return "Acci�n no valida para el item";
		}
		
		//Esto podr�a ser una exception
		if(effectsOver.contains("item"))
		{			
			return "Acci�n no v�lida sobre un item";
		}
		
		Trigger trigger = new Trigger("item", this.getName(), "", "");
		
		over.Execute(trigger);
		
		//-1 es usos ilimitados. Ej: una espada
		if (this.getUsesQty() != -1)
			this.setUsesQty(this.getUsesQty() - 1);
		
		return trigger.getOnTrigger();
	}
	
	
	@Override
	public String Execute(Trigger trigger) {
		/*
		 * Esto va comentado, porque creo que despues deber�amos tener un trigger ac�. La idea es para saber que le hace un item a otro item
		 * if (!triggers.contains(trigger))
			System.out.println("Accion no valida en el item");*/
		
		System.out.println(trigger.getOnTrigger());
		
		super.status = trigger.getAfterTrigger();
		
		return "";
	}
	
	
	public int getUsesQty() {
		return usesQty;
	}
	public void setUsesQty(int usesQty) {
		this.usesQty = usesQty;
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
