package ar.edu.unlam.halcones.entities;

public class Character {
	private Location location;
	private Inventory inventory;

	public Character(Location location) {
		this.location = location;
		this.inventory = new Inventory();
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
	
	public String interactWithNpc(Npc npc) throws Exception {
		if (!this.location.isNpcInLocation(npc)) {
			throw new Exception("No se encuentra el " + npc.getName() + " en el lugar");
		}
		return npc.getTalk();
	}
	
}
