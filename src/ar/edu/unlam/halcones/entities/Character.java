package ar.edu.unlam.halcones.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Character implements ITriggereable, INombrable<Character> {
	private Location location;
	private Inventory inventory;
	protected String status;
	private List<Trigger> triggers;

	public Character(Location location) {
		this.location = location;
		this.inventory = new Inventory();
	}

	public Character(List<Trigger> triggers) {
		this.triggers = triggers;
	}
	
	public void moveTo(Location otherLocation) {
		try {
			this.location = this.location.goTo(otherLocation);
			System.out.println(this.location.getDescription());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public String lookAround() {
		return location.getInformation();
	}

	public void agarrarItem(Item item) throws Exception {
		// Tengo que validar que el item se encuentra en algun place de la location en
		// la que me encuentro
		if (!this.location.isItemInLocation(item)) {
			throw new Exception("No se encuentra el item que desea agarrar");
		}
		this.inventory.add(item);
	}

	public void agarrarItem(Item item, Place place) throws Exception {
		// Tengo que validar que el item se encuentra en algun place de la location en
		// la que me encuentro
		if (!this.location.isItemInLocation(item, place)) {
			throw new Exception("No se encuentra el item que desea agarrar");
		}
		this.inventory.add(item);
	}

	public boolean isInLocation(Location otherLocation) {
		return this.location.equals(otherLocation);
	}

	public boolean isItemInInventory(Item item) {
		return this.inventory.hasItem(item);
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void usarItem(Item item, String action, Character over) throws Exception {
		if (!inventory.hasItem(item)) {
			throw new Exception("No tienes este item en tu inventario.");
		}
	
		item.Use(action, over);
		
		inventory.remove(item);
	}
	
	public void usarItem(Item item, String action, Npc over) throws Exception {
		if (!inventory.hasItem(item)) {
			throw new Exception("No tienes este item en tu inventario.");
		}
	
		item.Use(action, over);
		
		inventory.remove(item);
	}
	
	public void usarItem(Item item, String action, Item over) throws Exception {
		if (!inventory.hasItem(item)) {
			throw new Exception("No tienes este item en tu inventario.");
		}
	
		item.Use(action, over);
		
		inventory.remove(item);
	}
	
	@Override
	public String Execute(Trigger trigger) throws Exception {
		Optional<Trigger> aux = triggers.stream().filter(t -> t.getType().equals(trigger.getType()) && t.getThing().equals(trigger.getThing())).findAny();	
		
		if (!aux.isPresent())
		{
			throw new Exception("Accion no valida en el Character");
		}
		
		status = aux.get().getAfterTrigger();
		
		return aux.get().getOnTrigger();
	}

	public String interactWithNpc(Npc npc) throws Exception {
		if (!this.location.isNpcInLocation(npc)) {
			throw new Exception("No se encuentra el " + npc.getName() + " en el lugar");
		}
		return npc.getTalk();
	}

	@Override
	public Map<String, Character> getNombres() {
		Map<String,Character> myMap = new HashMap<String,Character>();
	    myMap.put("sobre mi", this);
	    myMap.put("en mi", this);
	    //myMap.put(this.name?, this);
	    
	    return myMap;	
	}
	
	@Override
	public void triggerThis(String action) throws Exception {
		
		for (Trigger triggers_IT : triggers) {
		
			if(triggers_IT.getType().contentEquals(action)) {
				this.Execute(triggers_IT);
				return;
			}
			
		}
		
	}
	
}
