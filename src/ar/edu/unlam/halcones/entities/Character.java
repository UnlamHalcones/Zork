package ar.edu.unlam.halcones.entities;

import java.util.List;
import java.util.Optional;

public class Character implements ITriggereable {
	private Location location;
	private Inventory inventory;
	protected String status;
	private List<Trigger> triggers;

	public Character(Location location) {
		this.location = location;
		this.inventory = new Inventory();
	}

	public Character(Location location, Inventory inventory) {
		this.location = location;
		this.inventory = inventory;
	}
	
	public Character(List<Trigger> triggers) {
		this.triggers = triggers;
	}

	public void moveTo(Location otherLocation) {
		this.location = this.location.goTo(otherLocation);
		if(!location.equals(this.location)) {
			System.out.println(this.location.getDescription());
		}
	}

	public String lookAround() {
		return location.getInformation();
	}

	public void agarrarItem(Item item) {
		// Tengo que validar que el item se encuentra en algun place de la location en
		// la que me encuentro
		if (this.location.isItemInLocation(item)) {
			this.inventory.add(item);
		} else {
			System.err.println("No se encuentra el item que desea agarrar");
		}
		
	}

	public void agarrarItem(Item item, Place place) {
		// Tengo que validar que el item se encuentra en algun place de la location en
		// la que me encuentro
		if (this.location.isItemInLocation(item, place)) {
			this.inventory.add(item);
		} else {
			System.err.println("No se encuentra el item que desea agarrar");
		}
	}

	public boolean isInLocation(Location otherLocation) {
		return this.location.equals(otherLocation);
	}

	public boolean isItemInInventory(Item item) {
		return this.inventory.hasItem(item);
	}

	public String usarItem(Item item, String action, ITriggereable over) {
		if (inventory.hasItem(item)) {
			inventory.remove(item);
			return item.use(action, over);
		} else {
			return "No tienes este item en tu inventario.";
		}
	}
	
	@Override
	public String execute(Trigger trigger) {
		Optional<Trigger> aux = triggers.stream()
				.filter(t -> t.getType().equals(trigger.getType()) && t.getThing().equals(trigger.getThing()))
				.findAny();

		if (!aux.isPresent()) {
			return "Accion no valida en el Character";
		}

		status = aux.get().getAfterTrigger();

		return aux.get().getOnTrigger();
	}

	public String interactWithNpc(Npc npc) {
		if (this.location.isNpcInLocation(npc)) {
			return npc.getTalk();
		}
		return "No se encuentra el " + npc.getName() + " en el lugar";
	}

	@Override
	public String getType() {
		return "self";
	}

}
