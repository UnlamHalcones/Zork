package ar.edu.unlam.halcones.entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Character implements ITriggereable, INombrable<Character> {
	public Inventory getInventory() {
		return inventory;
	}

	private Location location;
	private Inventory inventory;
	protected String status;
	private List<Trigger> triggers;

	public Character(Location location, Inventory inventory) {
		this.location = location;
		this.inventory = inventory;
		this.triggers = new LinkedList<>();
	}

	public Character(Location location) {
		this.location = location;
		this.inventory = new Inventory();
		this.triggers = new LinkedList<>();
	}

	public Character(List<Trigger> triggers) {
		this.triggers = triggers;
	}
	
	public String moveTo(Location otherLocation) {
		
		//VALIDO CARDINALIDAD
		switch(otherLocation.getName().toUpperCase()) {
			case "NORTE":
				Connection northConnection = this.location.getConnections().stream().filter(x -> x.getDirection().toUpperCase().equals("NORTE")).findAny().orElse(null);
				
				if(northConnection != null)
					otherLocation = northConnection.getLocation();
				else
					return "No puedo ir en esa dirección";
				
				break;
			case "SUR":
				Connection southConnection = this.location.getConnections().stream().filter(x -> x.getDirection().toUpperCase().equals("SUR")).findAny().orElse(null);
				
				if(southConnection != null)
					otherLocation = southConnection.getLocation();
				else
					return "No puedo ir en esa dirección";
				
				break;
			case "ESTE":
				Connection eastConnection = this.location.getConnections().stream().filter(x -> x.getDirection().toUpperCase().equals("ESTE")).findAny().orElse(null);
				
				if(eastConnection != null)
					otherLocation = eastConnection.getLocation();
				else
					return "No puedo ir en esa dirección";
				
				break;
			case "OESTE":
				Connection westConnection = this.location.getConnections().stream().filter(x -> x.getDirection().toUpperCase().equals("OESTE")).findAny().orElse(null);
				
				if(westConnection != null)
					otherLocation = westConnection.getLocation();
				else
					return "No puedo ir en esa dirección";
				
				break;
			default:
				break;
		}
		
		if(this.location.equals(otherLocation)) {
			return "Ya estas en esa ubicacion";
		}

		String response = this.location.goTo(otherLocation);

		if(response.equals("OK")) {
			this.location = otherLocation;
			return location.getInformation();
		}
		return response;
	}

	public String agarrarItem(Item item) {
		// Tengo que validar que el item se encuentra en algun place de la location en
		// la que me encuentro
		if (this.location.isItemInLocation(item)) {
			this.location.removeItem(item);
			return this.inventory.add(item);
		} else {
			return "No se encuentra el item que desea agarrar";
		}
	}

	public String agarrarItem(Item item, Place place) {
		// Tengo que validar que el item se encuentra en algun place de la location en
		// la que me encuentro
		if (this.location.isItemInLocation(item, place)) {
			this.location.removeItemFromPlace(item, place);
			return this.inventory.add(item);
		} else {
			return "No se encuentra el item que desea agarrar";
		}
	}

	public String mostrarInformacionDelInventario() {
		return this.inventory.showItems();
	}

	public boolean isInLocation(Location otherLocation) {
		return this.location.equals(otherLocation);
	}

	public boolean isItemInInventory(Item item) {
		return this.inventory.hasItem(item);
	}

	public String usarItem(Item item, String action, ITriggereable over) {
		if(inventory.hasItem(item)) {
			String use = item.use(action, over);
			inventory.remove(item);
			return use;
		} else {
			return "No tienes este item en tu inventario";
		}
	}

	public String interactWithNpc(Npc npc) {
		if (!this.location.isNpcInLocation(npc)) {
			return "No se encuentra el " + npc.getName() + " en el lugar";
		} else {
			return npc.getTalk();
		}
	}

	@Override
	public String execute(Trigger trigger) {
		Trigger triggerToExecute = triggers.stream()
				.filter(t -> t.getType().equals(trigger.getType()) && t.getThing().equals(trigger.getThing()))
				.findAny()
				.orElse(null);

		if (triggerToExecute == null) {
			return "Eso no ha servido de nada";
		}

		status = triggerToExecute.getAfterTrigger();
		return triggerToExecute.getOnTrigger();
	}

	@Override
	public String getType() {
		return "self";
	}

	@Override
	public Map<String, Character> getNombres() {
		Map<String,Character> myMap = new HashMap<String,Character>();
	    myMap.put("sobre mi", this);
	    myMap.put("en mi", this);
	    
	    return myMap;	
	}
	
	@Override
	public void triggerThis(String action) {
		
		for (Trigger triggers_IT : triggers) {
		
			if(triggers_IT.getType().contentEquals(action)) {
				this.execute(triggers_IT);
				return;
			}
			
		}
	}
	
	@Override 
	public Character getEntity() {
		return this;
	}

	@Override
	public String ver() {
		return location.getFullDescription();
	}
}
