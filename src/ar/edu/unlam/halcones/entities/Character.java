package ar.edu.unlam.halcones.entities;

public class Character implements ITriggereable {
	private Location location;
	private Inventory inventory;
	protected String status;
	

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
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	@Override
	public String Execute(Trigger trigger) {
		/*
		 * Esto va comentado, porque creo que despues deber�amos tener un trigger ac�. La idea es para saber que le hace un item a otro item
		 * if (!triggers.contains(trigger))
			System.out.println("Accion no valida en el personaje");*/
		
		//Esto de los estados podr�a ser una colecci�n, ejemplo: vidaMaximaIncrementada, ataqueIncrementado. O incluso un map
		status = trigger.getAfterTrigger();
		
		return trigger.getOnTrigger();
	}

}
